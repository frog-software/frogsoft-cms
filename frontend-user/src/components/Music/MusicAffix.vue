<template>
  <div style="z-index: 1000;padding:30px">
    <a-affix :offset-bottom="offset">

      <a-popover style="z-index: 1100" trigger="click" placement="right">
        <template v-slot:content>
          <a-card
            title="经典曲目"
            :bordered="false"
            style="justify-content: center; width:400px;text-align: center"
          >
            <a-button
              slot="extra"
              type="link"
              @click="$router.push({name:'Music'})"
            >
              进入方言歌曲库
            </a-button>

            <img :src="music.cover" alt="音乐封面" style="width: 95%;"/>

            <div> {{ musicTitle }}</div>

            <audio
              ref="myAudio"
              :src="music.source"
              style="width: 100%;"
              controls loop preload autoplay
            />

          </a-card>
        </template>

        <a-button shape="circle" size="large">
          <a-icon type="customer-service"/>
        </a-button>

      </a-popover>

    </a-affix>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'MusicAffix',
  data () {
    return {
      music: {
        id: 0,
        source: '',
        title: '',
        artist: '',
        cover: 'http://dummyimage.com/120x60',
        likes: 0
      }
    }
  },
  created () {
    axios.get('/music/' + this.musicID.toString()).then(res => {
      this.music = res.data.music
    })
  },
  computed: {
    musicID () {
      return this.$store.getters.music
    },
    musicTitle () {
      return this.music.artist + ' - ' + this.music.title
    },
    offset () {
      return 400 * document.documentElement.clientHeight / document.documentElement.clientWidth
    }
  },

  watch: {
    musicID () {
      axios.get('/music/' + this.musicID.toString()).then(res => {
        this.music = res.data.music
      })
    }
  }
}
</script>

<style scoped>

</style>
