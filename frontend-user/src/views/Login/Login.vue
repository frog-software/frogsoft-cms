<template>
  <div class="login">
    <a-row type="flex" justify="center">
      <a-col>
        <img src="@/assets/logo.png" width="300px" alt="E方言 —— Easy Dialect"/>
      </a-col>
    </a-row>
    <a-row type="flex" justify="center" style="padding-bottom:20px">
      <a-col>
        <h2 style="color:rgb(23, 7, 66)"> 莆仙方言在线工具 </h2>
      </a-col>
    </a-row>
    <a-row type="flex" justify="start" align="middle" :gutter="[0,20]">
      <a-col span="8">
        <div style="text-align: right; padding-right: 10px"> 用户名</div>
      </a-col>
      <a-col span="8">
        <a-input v-model="username" placeholder="输入用户名"/>
      </a-col>
      <a-col span="8"/>

      <a-col span="8">
        <div style="text-align: right; padding-right: 10px;"> 密码</div>
      </a-col>
      <a-col span="8">
        <a-input-password v-model="password" placeholder="输入密码" size="default"/>
      </a-col>
      <a-col span="8"/>

      <a-col span="8"/>
      <a-col span="4">
        <a-button shape="round" type="primary" :disabled="username===''||password===''" @click="login"> 登录</a-button>
      </a-col>
      <a-col span="4">
        <router-link to="./forget"> 忘记密码</router-link>
      </a-col>
    </a-row>

    <a-divider/>
    <a-row type="flex" justify="center" style="padding-bottom:20px">
      <a-col span="4">
        <p> 没有账号？</p>
      </a-col>
      <a-col span="4">
        <router-link to="./register"> 注册</router-link>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login () {
      axios.post('/login', {
        username: this.username,
        password: this.password
      }).then(res => {
        this.$store.commit('userLogin', res.data.id)
        localStorage.setItem('token', res.data.token)
        this.$message.success('登录成功')
        this.$router.push({ name: 'Home' })
      }).catch(err => {
        if (err.response.status === 401) {
          this.$message.destroy()
          this.$message.error('用户名或密码错误')
        }
      })
    }
  }
}
</script>

<style scoped>
.login {
  background: white;
  width: 80%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
}
</style>
