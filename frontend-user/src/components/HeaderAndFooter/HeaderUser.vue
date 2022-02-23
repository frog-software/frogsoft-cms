<script setup>
import UserOutlined from '@ant-design/icons-vue/UserOutlined';
import UserPage     from './UserPage.vue';
import store        from '../../store';
</script>
<template>
  <!--没有登录时显示的内容-->
  <div v-if="!hasLogin">
    <a-space
        align="baseline"
        direction="horizontal"
        size="middle"
    >
      <a-button
          class="link"
          size="small"
          type="link"
      >
        <router-link to="/login">
          登录
        </router-link>
      </a-button>
      <a-popover
          placement="bottom"
          trigger="hover"
      >
        <template #content>
          <div>请登录您的账户</div>
        </template>
        <UserOutlined/>
      </a-popover>
    </a-space>
  </div>

  <!--登录后显示的内容-->
  <div
      v-else
      class="user"
  >
    <a-popover
        placement="bottom"
        trigger="hover"
    >
      <template #content>
        <p>你好，亲爱的{{ username }}！</p>
      </template>
      <a-avatar
          :src="avatar"
          @click="store.commit('drawerVisibility',true)"
      />
    </a-popover>

    <a-drawer
        :visible="drawerVisibility"
        :z-index="1500"
        placement="right"
        style="text-align:center"
        width="700"
        @close="store.commit('drawerVisibility',false)"
    >
      <template #title>
        <img
            :src="avatar"
            alt="个人中心"
            width="40"
        >
      </template>

      <UserPage/>
    </a-drawer>
  </div>
</template>

<script>

export default {
  name: 'HeaderUser',
  components: {UserOutlined, UserPage},
  computed: {
    hasLogin() {
      return store.getters.loginStatus;
    },
    username() {
      return store.getters.user.username;
    },
    avatar() {
      return store.getters.user.avatar;
    },
    drawerVisibility: {
      get() {
        return store.getters.drawerVisibility;
      },
      set(value) {
        store.commit('drawerVisibility', value);
      },
    },
  },
};
</script>

<style scoped>
.link {
  color: black
}
</style>
