<template>

  <a-layout style="width: 1200px">
    <a-layout-header style="background: white">
      <myHeader/>
    </a-layout-header>
    <a-layout-content>
      <div class="body">
        <router-view/>
      </div>

      <MusicAffix/>
    </a-layout-content>
    <a-layout-footer style="background: rgb(46, 46, 46)">
      <myFooter/>
    </a-layout-footer>
  </a-layout>

</template>

<script>
import myHeader from './components/Header&&Footer/Header.vue'
import myFooter from './components/Header&&Footer/Footer.vue'
import store from '@/store'
import MusicAffix from '@/components/Music/MusicAffix'

export default {
  components: {
    myHeader,
    myFooter,
    MusicAffix
  },
  methods: {
    onResize () {
      setTimeout(function () {
        const width = document.documentElement.clientWidth
        document.body.style.zoom = Number(width / 12).toString() + '%'
      })
    }
  },
  beforeCreate () {
    document.addEventListener('DOMContentLoaded', () => {
      const width = document.documentElement.clientWidth
      document.body.style.zoom = Number(width / 12).toString() + '%'
    })
  },
  created () {
    if (window.localStorage.getItem('id')) {
      store.commit('userLogin', window.localStorage.getItem('id'))
    }
  },
  mounted () {
    window.addEventListener('resize', this.onResize)
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.onResize)
  }

}
</script>
<style>
.body {
  margin: 0 auto;
  width: 80%;
}
</style>
