<template>
  <div>
    <a-list
        :data-source="listSource"
        :pagination="pagination"
        :loading="{spinning: loading, delay: 200}"
        item-layout="vertical"
    >
      <template #renderItem="{item}">
        <a-list-item>
          <template #extra>
            <img
                :src="item?.cover"
                alt="文章封面"
                width="300"
            >
          </template>
          <a-list-item-meta :description="item?.description">
            <template #title>
              <h2>
                <router-link :to="{name:'ArticleDetails',params:{id:item?.id.toString()}}">
                  {{ item?.title }}
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
import axios from 'axios';

export default {
  name: 'ArticleList',
  props: {
    pageSize: Number,
  },
  data() {
    return {
      listSource: [],
      loading: false,
      currentPage: 1,
      totalElements: 0,
    };
  },
  computed: {
    pagination() {
      return {
        onChange: (page) => {
          this.getCurrentPageData(page);
        },
        pageSize: this.pageSize,
        total: this.totalElements,
        current: this.currentPage,
      };
    },
    queryParmas() {
      return {
        page: this.currentPage - 1,
        size: this.pageSize
      }
    }
  },
  created() {
    this.getCurrentPageData(1)
  },
  methods: {
    getCurrentPageData(page) {
      this.currentPage = page
      this.loading     = true;
      axios.get('/v1/articles', {params: this.queryParmas}).then((res) => {
        this.listSource    = res.data._embedded.articleDtoList;
        this.totalElements = res.data.page.totalElements
      }).finally(
          () => {
            this.loading = false;
          },
      );
    },

  },

};
</script>
<style></style>
