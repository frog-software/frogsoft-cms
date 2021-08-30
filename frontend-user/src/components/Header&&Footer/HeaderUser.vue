<template>
  <!--没有登录时显示的内容-->
  <div v-if="!hasLogin">
    <a-space align="baseline" direction="horizontal" size="middle">
      <a-button class="link" size=small type="link">
        <router-link to="/login">登录</router-link>
      </a-button>
      <a-popover trigger="hover" placement="bottom">
        <template v-slot:content>
          <div>请登录您的账户</div>
        </template>
        <a-icon type="user"/>
      </a-popover>
    </a-space>
  </div>

  <!--登录后显示的内容-->
  <div v-else class="user">

    <a-popover trigger="hover" placement="bottom">
      <template v-slot:content>
        <p>你好，亲爱的{{ username }}！</p>
      </template>
      <a-avatar :src="avatar" v-on:click="$store.commit('drawerVisibility',true)"/>
    </a-popover>

    <a-drawer
      :visible="drawerVisibility"
      :z-index="1500"
      placement="right"
      style="text-align:center"
      width="700"
      @close="$store.commit('drawerVisibility',false)"
    >
      <template v-slot:title>
        <img :src="avatar" alt="个人中心" width="40"/>
      </template>

      <UserPage/>
    </a-drawer>

  </div>
</template>

<script>
import UserPage from '@/components/Header&&Footer/UserPage'

export default {
  name: 'HeaderUser',
  components: {
    UserPage
  },
  computed: {
    hasLogin () {
      return this.$store.getters.loginStatus
    },
    username () {
      return this.$store.getters.user.username
    },
    avatar () {
      return this.$store.getters.user.avatar
    },
    drawerVisibility: {
      get () {
        return this.$store.getters.drawerVisibility
      },
      set (value) {
        this.$store.commit('drawerVisibility', value)
      }
    }
  }
}
</script>

<style scoped>
.link {
  color: black
}
</style>
