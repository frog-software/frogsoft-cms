<script setup>
import MyHeader from './components/HeaderAndFooter/Header.vue';
import MyFooter from './components/HeaderAndFooter/Footer.vue';
</script>
<template>
  <div>
    <a-layout style="width: 1200px">
      <a-layout-header style="background: white">
        <MyHeader/>
      </a-layout-header>
      <a-layout-content>
        <div class="body">
          <router-view/>
        </div>
      </a-layout-content>
      <a-layout-footer style="background: rgb(46, 46, 46)">
        <MyFooter/>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script>

import store from './store';

export default {
  beforeCreate() {
    document.addEventListener('DOMContentLoaded', () => {
      const width = document.documentElement.clientWidth;
      document.body.style.zoom = `${Number(width / 12).toString()}%`;
    });
  },
  created() {
    if (window.localStorage.getItem('id')) {
      store.commit('userLogin', window.localStorage.getItem('id'));
    }
  },
  mounted() {
    window.addEventListener('resize', this.onResize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.onResize);
  },

  methods: {
    onResize() {
      setTimeout(() => {
        const width = document.documentElement.clientWidth;
        document.body.style.zoom = `${Number(width / 12).toString()}%`;
      });
    },
  },

};
</script>
<style>
.body {
  margin: 0 auto;
  width: 80%;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
