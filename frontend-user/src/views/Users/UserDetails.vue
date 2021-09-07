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
            v-show=" parseInt($route.params.id) === $store.getters.user.id"
            type="primary"
        >
          <router-link :to="{name:'UserSettings'}">
            修改个人信息
          </router-link>
        </a-button>
      </template>

      <a-descriptions :column="1">
        <a-descriptions-item label="用户名">
          {{ user.username }}
        </a-descriptions-item>
        <a-descriptions-item label="昵称">
          {{ user.nickname }}
        </a-descriptions-item>
        <a-descriptions-item label="头像">
          <a-avatar
              :size="64"
              :src="user.avatar"
              shape="circle"
          />
        </a-descriptions-item>
      </a-descriptions>

      <a-descriptions layout="vertical">
        <a-descriptions-item label="生日">
          {{ user.birthday }}
        </a-descriptions-item>
        <a-descriptions-item label="邮箱">
          {{ user.email }}
        </a-descriptions-item>
        <a-descriptions-item label="上次登录时间">
          {{ user.login_time }}
        </a-descriptions-item>
        <a-descriptions-item label="电话">
          {{ user.telephone }}
        </a-descriptions-item>
      </a-descriptions>

      <a-tabs>
        <a-tab-pane
            key="1"
            tab="ta创作的文章"
        >
          <ArticleList
              :list-data="publish_articles"
              :page-size="6"
          />
        </a-tab-pane>
        <a-tab-pane
            key="2"
            tab="ta收藏的文章"
        >
          <ArticleList
              :list-data="like_articles"
              :page-size="6"
          />
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </a-spin>
</template>

<script>
import axios from 'axios';
import ArticleList from '../../components/Articles/ArticleList.vue';

export default {
  name: 'UserDetails',
  components: {
    ArticleList,
  },
  props: {
    id: String,
  },
  data() {
    return {
      spinning: true,
      user: {
        id: 0,
        username: 'username',
        nickname: 'nickname',
        email: 'pxm@edialect.top',
        telephone: '',
        registration_time: '2001-01-01 00:00:00',
        login_time: '2001-01-01 00:00:00',
        birthday: '',
        avatar: 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png',
        county: '',
        town: '',
        is_admin: false,
      },
      publish_articles: [],
      like_articles: [],
    };
  },
  created() {
    axios.get(`/v1/users/${this.id}`).then((res) => {
      this.user = res.data.user;
      this.publish_articles = res.data.publish_articles;
      this.like_articles = res.data.like_articles;
      this.spinning = false;
    });
  },
};
</script>

<style scoped>

</style>
