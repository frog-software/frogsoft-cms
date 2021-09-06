<template>
  <div>
    <a-card title="热门文章">
      <div
          v-for="item in hotArticles"
          :key="item.article.id"
      >
        <a-card-grid style="width:50%;text-align:center">
          <router-link :to="{name:'ArticleDetails',params:{id:item.article.id.toString()}}">
            <a-card :bordered="false">
              <template #cover>
                <img
                    :alt="item.article.title"
                    :src="item.article.cover"
                    style="width:100%;height:150px"
                >
              </template>
              <a-card-meta :title="item.article.title"/>
            </a-card>
          </router-link>
        </a-card-grid>
      </div>
    </a-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HotArticles',
  data() {
    return {
      hotArticles: [],
    };
  },
  created() {
    axios.get('/website/hot_articles').then((res) => {
      this.hotArticles = res.data.hot_articles;
    });
  },
};
</script>

<style scoped>

</style>
