<script setup>

import ArticleList from '../../components/Articles/ArticleList.vue'; </script>

<template>
  <div class="body">
    <a-spin
        :delay="500"
        :spinning="spinning"
    >
      <ArticleList
          :list-data="listData"
          :page-size="8"
      />
    </a-spin>
  </div>
</template>

<script>
// eslint-disable-next-line import/order
import axios from 'axios';

export default {
  name: 'Articles',
  data() {
    return {
      listData: [],
      spinning: false,
    };
  },
  created() {
    this.spinning = true;
    axios.get('/articles').then((res) => {
      this.listData = res.data.articles;
    }).finally(() => {
      this.spinning = false;
    });
  },
};
</script>

<style scoped>
.body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  padding: 40px;
}
</style>
