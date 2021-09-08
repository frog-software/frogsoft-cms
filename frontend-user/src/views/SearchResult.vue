<script setup>
import ArticleList from '../components/Articles/ArticleList.vue';
</script>
<template>

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
        :search-content="keyWords"
        :page-size="8"
    />
  </a-card>
</template>

<script>
import axios from 'axios';

export default {
  name: 'SearchResult',
  props: {
    keyWords: String,
  },
  data() {
    return {
      searchContent: '',
      search: ''
    };
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
    },
  },

};
</script>

<style scoped>

</style>
