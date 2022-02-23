<template>
  <a-card>
    <template #title>
      <a-input-search
          v-model:value="searchContent"
          enter-button
          placeholder="请输入搜索内容"
          size="large"
          @search="$router.push({name:'Search',query:{key:searchContent}})"
      />
    </template>
    <ArticleList
        :page-size="8"
        :search-content="keyWords"
    />
  </a-card>
</template>

<script>
import ArticleList from '../components/Articles/ArticleList.vue';

export default {
  name: 'SearchResult',
  components: {ArticleList},
  props: {
    keyWords: String,
  },
  data() {
    return {
      searchContent: '',
      search: '',
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
