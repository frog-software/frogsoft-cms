<template>
  <div>
    <a-row justify="end" align="middle" :gutter="8">
      <a-col>
        排序方法
      </a-col>
      <a-col>
        <a-radio-group v-model:value="sortBy" @change="getCurrentPageData(1)">
          <a-radio-button value="publishDate">
            更新时间
          </a-radio-button>
          <a-radio-button value="id">
            发布时间
          </a-radio-button>
          <a-radio-button value="author">
            作者
          </a-radio-button>
        </a-radio-group>
      </a-col>
      <a-col>
        <a-radio-group v-model:value="order" @change="getCurrentPageData(1)">
          <a-radio-button value="DESC">
            <CaretUpOutlined/>
          </a-radio-button>
          <a-radio-button value="ASC">
            <CaretDownOutlined/>
          </a-radio-button>
        </a-radio-group>
      </a-col>
    </a-row>
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
import axios                                from 'axios';
import {CaretUpOutlined, CaretDownOutlined} from "@ant-design/icons-vue";

export default {
  components: {CaretDownOutlined, CaretUpOutlined},
  name: 'ArticleList',
  props: {
    pageSize: Number,
    searchContent: String,
  },
  data() {
    return {
      listSource: [],
      loading: false,
      currentPage: 1,
      totalElements: 0,
      sortBy: 'publishDate',
      order: 'DESC'
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
      let result = {
        page: this.currentPage - 1,
        size: this.pageSize,
        sortBy: this.sortBy,
        order: this.order
      }
      if (this.searchContent) result.search = this.searchContent
      return result
    }
  },
  created() {
    this.getCurrentPageData(1)
  },
  watch: {
    searchContent() {
      this.getCurrentPageData(1)
    }
  },
  methods: {
    getCurrentPageData(page) {
      this.currentPage = page
      this.loading     = true;
      axios.get('/v1/articles', {params: this.queryParmas}).then((res) => {
        this.listSource    = res.data._embedded?.articleDtoList || [];
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
