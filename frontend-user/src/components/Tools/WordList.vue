<template>
  <a-list
    :pagination="pagination"
    :data-source="listSource || []"
    :loading="{spinning: loading, delay: 500}"
  >
    <template v-slot:renderItem="item">
        <router-link :to="{name:'WordDetails',params:{id:item.word.id.toString()}}">
        <a-card
          :hoverable="true"
          style="width: 100%;"
          class="cccc"
        >
        <a-card-meta :description="item.word.definition.slice(0,100)">
          <template v-slot:title>
            <h2>
                {{ item.word.word }}
            </h2>
          </template>
        </a-card-meta>
        </a-card>
        </router-link>
    </template>
  </a-list>
</template>
<script>
import axios from 'axios'

export default {
  name: 'WordList',
  data () {
    return {
      listSource: null,
      listDataLock: null,
      loading: false,
      currentPage: 1
    }
  },
  computed: {
    pagination () {
      return {
        onChange: page => {
          this.currentPage = page
          this.getCurrentPageData(page)
        },
        pageSize: this.pageSize,
        total: this.listDataLock ? this.listDataLock.length : 0,
        current: this.currentPage
      }
    }
  },
  props: {
    listData: Array,
    pageSize: Number
  },
  created () {
    this.listDataLock = Object.assign([], this.listData)
  },
  watch: {
    listData () {
      this.listDataLock = Object.assign([], this.listData)
      this.currentPage = 1
    },
    listDataLock () {
      if (!this.listDataLock) return
      if (this.listDataLock.length === 0) return
      this.getCurrentPageData(1)
    }
  },
  methods: {
    getCurrentPageData (page) {
      this.loading = true
      axios.put('/words', {
        words: this.listData.slice((page - 1) * this.pageSize, page * this.pageSize)
      }).then(res => {
        this.listSource = res.data.words
      }).finally(
        () => {
          this.loading = false
        }
      )
    }
  }
}
</script>
<style >

</style>
