<template>
  <a-spin
      :spinning="spinning"
      class="body"
  >
    <a-card>
      <template #title>
        <h2>用户信息</h2>
      </template>

      <template #extra>
        <a-button
            v-show="$route.params.username=== store.getters.user.username"
            type="primary"
        >
          <router-link :to="{name:'UserSettings'}">
            修改个人信息
          </router-link>
        </a-button>
      </template>
      <a-descriptions :column="1">
        <a-descriptions-item label="用户名">
          {{ username }}
        </a-descriptions-item>

        <a-descriptions-item label="头像">
          <a-avatar
              :size="64"
              :src="avatar"
              shape="circle"
          />
        </a-descriptions-item>
        <a-descriptions-item label="邮箱">
          {{ email }}
        </a-descriptions-item>
        <a-descriptions-item label="权限">
          {{ is_admin ? "管理员" : "普通用户" }}
        </a-descriptions-item>
      </a-descriptions>

      <a-descriptions :column="2">

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
            tab="创作文章"
        >
          <ArticleListCreated
              :username="username"
              :page-size="8"
          />
        </a-tab-pane>
        <a-tab-pane
            key="2"
            tab="收藏列表"
        >
          <ArticleListFavorited
              :username="username"
              :page-size="8"
          />
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </a-spin>
</template>

<script>
import axios                from 'axios';
import store                from "../../store";
import ArticleListCreated   from "../../components/Articles/ArticleListCreated.vue";
import ArticleListFavorited from "../../components/Articles/ArticleListFavorited.vue";

export default {
  name: 'UserDetails',
  props: {
    username: String,
  },
  components: {ArticleListFavorited, ArticleListCreated},
  data() {
    return {
      spinning: false,
      is_admin: false,
      email: 'pxm@edialect.top',
      avatar: 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png',
      publishArticles: [],
      favoriteArticles: [],
      statistics: {}
    };
  },
  created() {
    this.getUserDetails(this.username)
  },
  methods: {
    getUserDetails(username) {
      this.spinning = true
      axios.get(`/v1/users/${username}`).then((res) => {
        this.email            = res.data.email;
        this.is_admin         = res.data.roles.includes('ROLE_ADMIN');
        this.avatar           = res.data.avatar || '/avatar.png'
        this.publishArticles  = res.data.publishArticles;
        this.favoriteArticles = res.data.favoriteArticles;
        this.statistics       = res.data.statistics;
        this.spinning         = false;
      });
    }
  },
  watch: {
    $route() {
      this.getUserDetails(this.username)
    }
  }
};
</script>

<style scoped>

</style>
