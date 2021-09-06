<script setup>
import ArticleList from '../components/Articles/ArticleList.vue';
</script>
<template>
  <a-spin
      :spinning="spinning"
      :delay="500"
      style="width: 90%"
  >
    <a-card>
      <template #title>
        <a-input-search
            v-model:value="searchContent"
            placeholder="请输入搜索内容"
            enter-button
            size="large"
            @search="$router.push({name:'Search',query:{key:searchContent}})"
        />
      </template>
      <ArticleList
          :list-data="articles"
          :page-size="8"
      />
    </a-card>
  </a-spin>
</template>

<script>
/* eslint-disable import/order */

import axios from 'axios';

export default {
  name: 'SearchResult',
  props: {
    keyWords: String,
  },
  data() {
    return {
      loading: {
        articles: false,
        words: false,
      },
      articles: [],
      words: [],
      searchContent: '',
    };
  },
  computed: {
    spinning() {
      let result = false;
      this.loading.forEach((value) => {
        if (value === true) result = true;
      });
      return result;
    },
  },

  watch: {
    keyWords() {
      this.loadingData();
    },
  },
  created() {
    this.loadingData();
  },
  methods: {
    loadingData() {
      this.searchContent = this.keyWords;
      Object.keys(this.loading).forEach((i) => {
        this.loading[i] = true;
      });
      axios.get('/articles', {params: {search: this.keyWords}}).then((res) => {
        this.articles = res.data.articles;
      }).finally(() => {
        this.loading.articles = false;
      });
      axios.get('/words', {params: {search: this.keyWords}}).then((res) => {
        this.words = res.data.words;
      }).finally(() => {
        this.loading.words = false;
      });
    },
  },

};
</script>

<style scoped>

</style>
