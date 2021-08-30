<template>
  <a-spin :spinning="drawerLoading">
    <a-descriptions layout="vertical">
      <template v-slot:title>
        <router-link :to="{name:'UserDetails',params:{id:user.id.toString()}}">用户信息</router-link>
        <a-button type="dashed" style="float: right;">
          <router-link :to="{ name: 'UserSettings'}">修改</router-link>
        </a-button>
      </template>

      <a-descriptions-item label="用户名">
        {{ user.username }}
      </a-descriptions-item>
      <a-descriptions-item label="邮箱">
        {{ user.email }}
      </a-descriptions-item>
      <a-descriptions-item label="上次登陆时间">
        {{ user.login_time }}
      </a-descriptions-item>
    </a-descriptions>

    <a-tabs>
      <a-tab-pane key="1" tab="我创作的文章">
        <ArticleList :listData="publish_articles" :page-size="3"/>
      </a-tab-pane>
      <a-tab-pane key="2" tab="收藏列表">
        <ArticleList :listData="like_articles" :page-size="3"/>
      </a-tab-pane>
      <template v-slot:tabBarExtraContent>
        <router-link :to="{name:'ArticleCreate'}"> 进入创作中心</router-link>
      </template>
    </a-tabs>

    <a-button
      type="danger"
      @click="$store.commit('userLogout');$router.go(0)"
    >
      登出
    </a-button>
  </a-spin>
</template>

<script>
import ArticleList from '@/components/Articles/ArticleList'
import { mapGetters } from 'vuex'

export default {
  name: 'UserPage',
  components: {
    ArticleList
  },
  computed: {
    ...mapGetters({
      user: 'user',
      publish_articles: 'publish_articles',
      like_articles: 'like_articles',
      drawerLoading: 'drawerLoading'
    })
  }
}
</script>

<style scoped>

</style>
