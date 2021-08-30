<template>
  <a-spin
    :spinning="spinning"
    :delay="500"
    style="width: 90%"
  >
    <a-card>
      <template slot="title">
        <a-input-search
          placeholder="请输入搜索内容"
          enter-button
          size="large"
          @search="$router.push({name:'Search',query:{key:searchContent}})"
          v-model="searchContent"
        />
      </template>
      <WordList :list-data="words" :page-size="8"/>
      <ArticleList :list-data="articles" :page-size="8"/>
    </a-card>
  </a-spin>
</template>

<script>
import WordList from '@/components/Tools/WordList'
import ArticleList from '@/components/Articles/ArticleList'
import axios from 'axios'

export default {
  name: 'SearchResult',
  components: {
    ArticleList,
    WordList
  },
  props: [
    'keyWords'
  ],
  computed: {
    spinning () {
      for (const key in this.loading) {
        if (this.loading[key] === true) return true
      }
      return false
    }
  },
  data () {
    return {
      loading: {
        articles: false,
        words: false
      },
      articles: [],
      words: [],
      searchContent: ''
    }
  },
  created () {
    this.loadingData()
  },

  watch: {
    keyWords () {
      this.loadingData()
    }
  },
  methods: {
    loadingData () {
      this.searchContent = this.keyWords
      for (const i in this.loading) {
        this.loading[i] = true
      }
      axios.get('/articles', { params: { search: this.keyWords } }).then(res => {
        this.articles = res.data.articles
      }).finally(() => {
        this.loading.articles = false
      })
      axios.get('/words', { params: { search: this.keyWords } }).then(res => {
        this.words = res.data.words
      }
      ).finally(() => {
        this.loading.words = false
      })
    }
  }

}
</script>

<style scoped>

</style>
