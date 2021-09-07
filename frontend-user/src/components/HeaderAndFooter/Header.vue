<script setup>
import HeaderUser from './HeaderUser.vue';
import store from '../../store';
</script>
<template>
  <div class="header">
    <a-row
        align="middle"
        justify="space-around"
        type="flex"
    >
      <a-col :span="3">
        <router-link to="/Home">
          <img
              alt="E方言图标"
              "src="store.getter"
              width="120"
          >
        </router-link>
      </a-col>

      <a-col :span="9">
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

          <a-menu-item key="Tools">
            <router-link :to="{name:'Tools'}">
              工具
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
