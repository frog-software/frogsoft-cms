<script setup>
import store                from '../../store';
import ArticleListFavorited from '../Articles/ArticleListFavorited.vue';
import ArticleListCreated   from "../Articles/ArticleListCreated.vue";
</script>
<template>
  <a-spin :spinning="drawerLoading">
    <a-descriptions layout="vertical">
      <template #title>
        <router-link :to="{name:'UserDetails',params:{username:user.username}}">
          用户信息
        </router-link>
        <a-button
            style="float: right;"
            type="dashed"
        >
          <router-link :to="{ name: 'UserSettings'}">
            修改
          </router-link>
        </a-button>
      </template>

      <a-descriptions-item label="用户名">
        {{ user.username }}
      </a-descriptions-item>
      <a-descriptions-item label="邮箱">
        {{ user.email }}
      </a-descriptions-item>
      <a-descriptions-item label="权限">
        {{ user.is_admin ? "管理员" : "普通用户" }}
      </a-descriptions-item>
      <a-descriptions-item label="文章发布量">
        {{ statistics.publishArticlesNum }}
      </a-descriptions-item>
      <a-descriptions-item label="文章总被阅读量">
        {{ statistics.viewsNum }}
      </a-descriptions-item>
      <a-descriptions-item label="文章总被点赞量">
        {{ statistics.likesNum }}
      </a-descriptions-item>
      <a-descriptions-item label="文章总被收藏量">
        {{ statistics.favoritesNum }}
      </a-descriptions-item>
    </a-descriptions>

    <a-tabs>
      <a-tab-pane
          key="1"
          tab="我创作的文章"
      >
        <ArticleListCreated
            :username="store.getters.user.username"
            :page-size="4"
        />
      </a-tab-pane>
      <a-tab-pane
          key="2"
          tab="收藏列表"
      >
        <ArticleListFavorited
            :username="store.getters.user.username"
            :page-size="4"
        />
      </a-tab-pane>
      <template #tabBarExtraContent>
        <router-link :to="{name:'ArticleCreate'}">
          进入创作中心
        </router-link>
      </template>
    </a-tabs>

    <a-button
        danger
        type="primary"
        @click="store.commit('userLogout');$router.go(0)"
    >
      登出
    </a-button>
  </a-spin>
</template>

<script>
import {mapGetters} from 'vuex';

export default {
  name: 'UserPage',
  computed: {
    ...mapGetters({
      user: 'user',
      statistics: 'statistics',
      publishArticles: 'publishArticles',
      favoriteArticles: 'favoriteArticles',
      drawerLoading: 'drawerLoading',
    }),
  },
};
</script>

<style scoped>

</style>
