<template>
  <div class="body">
    <a-card title="方言歌曲库">
      <p>说到福建的歌曲，或许大家只会想到《爱拼才会赢》。</p>
      <p>但实际上，莆仙方言也有很多好听的歌曲，让我们一起去听听吧~</p>
    </a-card>
    <a-card>
      <div v-for="item in musicList" :key="item.music.id">
        <a-card-grid style="width:50%;text-align:center">
          <a-card>
            <template v-slot:cover>
              <img :alt="item.music.title" :src="item.music.cover" style="height:300px;object-fit: contain"/>
            </template>
            <a-card-meta
              :title="item.music.artist+'-'+item.music.title">
              <div slot="description">
                音乐贡献者：
                <router-link :to="{name:'UserDetails',params:{id:item.contributor.id.toString()}}">
                  <a-avatar :src="item.contributor.avatar"/>
                  {{ item.contributor.nickname }}
                </router-link>
              </div>
            </a-card-meta>
            <template slot="actions" class="ant-card-actions">
              <div>
                <a-icon key="like" type="like"/>
                {{ item.music.likes }}
              </div>
              <a-icon key="play" type="play-circle" @click="$store.commit('changeMusic',item.music.id)"/>
              <a-icon key="ellipsis" type="ellipsis"/>
            </template>
          </a-card>
        </a-card-grid>
      </div>
    </a-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'MusicList',
  data () {
    return {
      musicList: [
        {
          music: {
            id: 0,
            source: 'https://hnzmfy.sy/jsmhg',
            cover: 'http://dummyimage.com/400x300',
            likes: 0,
            contributor: 0,
            visibility: false,
            title: '样例音乐',
            artist: '样例艺术家'
          },
          contributor: {
            nickname: '样例昵称',
            avatar: 'http://dummyimage.com/100x100',
            id: 0
          }
        }
      ]
    }
  },
  created () {
    axios.get('/music').then(res => {
      axios.put('music', { music: res.data.music }).then(res2 => {
        this.musicList = res2.data.music
      })
    })
  }
}
</script>

<style scoped>
.body {
  width: 85%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
}
</style>
