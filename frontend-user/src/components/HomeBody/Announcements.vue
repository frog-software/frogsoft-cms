<template>
  <div>
    <a-card title="热门文章">
      <div
          v-for="item in hotArticles"
      >
        <a-card-grid style="width:50%;text-align:center">
          <router-link :to="{name:'ArticleDetails',params:{id:item.id}}">
            <a-card :bordered="false">
              <template #cover>
                <img
                    :alt="item.title"
                    :src="item.cover"
                    style="width:100%;height:150px"
                >
              </template>
              <a-card-meta :title="item.title"/>
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
  name: 'Announcements',
  data() {
    return {
      hotArticles: [],
    };
  },
  created() {
    axios.get('/v1/home/announcements').then((res) => {
      this.hotArticles = res.data._embedded.articleDtoList;
    });
  },
};
</script>

<style scoped>

</style>
