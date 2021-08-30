<template>
  <div class="header">
    <a-row align="middle" justify="space-around" type="flex">
      <a-col :span="3">
        <router-link to="/Home">
          <img src="@/assets/logo.png" alt="E方言图标" width="120px">
        </router-link>
      </a-col>

      <a-col :span="9">
        <a-menu mode="horizontal" v-model="tab">
          <a-menu-item key="Home">
            <router-link :to="{name:'Home'}">主页</router-link>
          </a-menu-item>

          <a-menu-item key="Articles">
            <router-link :to="{name:'Articles'}">文章</router-link>
          </a-menu-item>

          <a-menu-item key="Tools">
            <router-link :to="{name:'Tools'}">工具</router-link>
          </a-menu-item>
          <a-menu-item key="music">
            <router-link to="/music">音乐</router-link>
          </a-menu-item>
        </a-menu>
      </a-col>

      <a-col :span="6">
        <a-input-search
          v-model="searchContent"
          placeholder="开始搜索"
          @search="$router.push({ name: 'Search', query: { key: searchContent } })"
        />
      </a-col>

      <a-col :span="2">
        <header-user/>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import headerUser from './HeaderUser.vue'

export default {
  name: 'Header',
  components: {
    headerUser
  },
  data () {
    return {
      searchContent: ''
    }
  },
  computed: {
    tab: {
      get () {
        return this.$store.getters.tab
      },
      set (value) {
        this.$store.commit('tab', value)
      }
    }
  }
}
</script>

<style scoped>

</style>
