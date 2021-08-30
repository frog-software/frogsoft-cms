<template>
  <a-spin
    :spinning="loading"
  :delay="500">
  <a-collapse
    :bordered="false"
    style="font-size: 22px;padding: 10px;background-color: white"
    :activeKey="activeKey"
  >
      <a-collapse-panel
        v-for="(value,key) in listSource "
        :header="key"
        :key="key"
      >
        {{value.map(item=>item.character).join("&nbsp;&nbsp;&nbsp;&nbsp;")}}
      </a-collapse-panel>
  </a-collapse>
  </a-spin>
</template>
<script>
import axios from 'axios'

export default {
  name: 'CharacterList',
  data () {
    return {
      listSource: {},
      listDataLock: null,
      loading: false,
      activeKey: []
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
    },
    listDataLock () {
      if (!this.listDataLock) return
      if (this.listDataLock.length === 0) return
      this.getCurrentData()
    }
  },
  methods: {
    getCurrentData () {
      if (this.listDataLock.length > 1250) { this.loading = true }
      axios.put('/characters', {
        characters: this.listData
      }).then(res => {
        this.listSource = {}
        res.data.characters.forEach((item) => {
          if (item.pinyin in this.listSource) this.listSource[item.pinyin].push(item)
          else this.listSource[item.pinyin] = [item]
        })
        this.activeKey = Object.keys(this.listSource)
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
