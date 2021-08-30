<template>
  <div>
    <a-list
      :data-source="listSource || []"
      :pagination="pagination"
      :loading="{spinning: loading, delay: 500}"
      item-layout="vertical"
    >
      <template v-slot:renderItem="item">
        <a-list-item>
          <template v-slot:extra>
            <img
              :src="item.article.cover"
              alt="文章封面"
              width="300"
            />
          </template>
          <a-list-item-meta :description="item.article.description">
            <template v-slot:title>
              <h2>
                <router-link :to="{name:'ArticleDetails',params:{id:item.article.id.toString()}}">
                  {{ item.article.title }}
                </router-link>
              </h2>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </template>
    </a-list>

  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'ArticleList',
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
      axios.put('/articles', {
        articles: this.listData.slice((page - 1) * this.pageSize, page * this.pageSize)
      }).then(res => {
        this.listSource = res.data.articles
      }).finally(
        () => {
          this.loading = false
        }
      )
    }

  }

}
</script>
<style></style>
