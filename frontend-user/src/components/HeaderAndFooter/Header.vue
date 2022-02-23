<script setup>
import HeaderUser from './HeaderUser.vue';
import store      from '../../store';
</script>
<template>
  <div class="header">
    <a-row
        align="middle"
        justify="space-around"
        type="flex"
    >
      <a-col :span="5">
        <router-link to="/Home">
          <img
              :src="store.getters.config.headerDto.logo"
              alt="网站页眉LOGO"
              style="max-height: 64px;max-width: 200px"
          >
        </router-link>
      </a-col>

      <a-col :span="6">
        <a-menu
            v-model:selectedKeys="tab"
            mode="horizontal"
        >
          <a-menu-item key="Home">
            <router-link :to="{name:'Home'}">
              主页
            </router-link>
          </a-menu-item>

          <a-menu-item key="Articles">
            <router-link :to="{name:'Articles'}">
              文章
            </router-link>
          </a-menu-item>
        </a-menu>
      </a-col>

      <a-col :span="6">
        <a-input-search
            v-model:value="searchContent"
            placeholder="开始搜索"
            @search="$router.push({ name: 'Search', query: { key: searchContent } })"
        />
      </a-col>

      <a-col :span="2">
        <HeaderUser/>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      searchContent: '',
    };
  },
  computed: {
    tab: {
      get() {
        return store.getters.tab;
      },
      set(value) {
        store.commit('tab', value);
      },
    },
  },
};
</script>

<style scoped>

</style>
