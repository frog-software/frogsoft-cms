<script setup>
import store from "../../store";
</script>
<template>
  <div class="login">
    <a-row
        align="middle"
        justify="center"
        type="flex"
    >
      <a-col>
        <img
            :src="store.getters.config.logo"
            :width="300"
        >
      </a-col>
    </a-row>
    <a-row
        align="middle"
        justify="center"
        style="padding-bottom:20px"
        type="flex"
    >
      <a-col>
        <h2 style="color:rgb(23, 7, 66)">
          {{ store.getters.config.title }}
        </h2>
      </a-col>
    </a-row>
    <a-row
        justify="center"
        type="flex"
    >
      <a-col>
        <h3>
          用户名
        </h3>
      </a-col>
    </a-row>
    <a-row
        justify="center"
        style="padding-bottom:10px"
        type="flex"
    >
      <a-col span="10">
        <a-input
            v-model:value="username"
            :max-length="50"
            placeholder="输入用户名"
        />
      </a-col>
    </a-row>
    <a-row
        justify="center"
        type="flex"
    >
      <a-col>
        <h3>
          密码
        </h3>
      </a-col>
    </a-row>
    <a-row
        justify="center"
        style="padding-bottom:10px"
        type="flex"
    >
      <a-col span="10">
        <a-input-password
            v-model:value="password"
            placeholder="输入密码"
            size="default"
        />
      </a-col>
    </a-row>
    <a-row
        justify="center"
        style="padding-bottom:10px"
        type="flex"
    >
      <a-col span="10">
        <a-input-password
            v-model:value="repeatedPassword"
            placeholder="请再次输入"
            size="default"
        />
      </a-col>
    </a-row>
    <a-row
        justify="center"
        type="flex"
    >
      <a-col>
        <h3>
          邮箱验证
        </h3>
      </a-col>
    </a-row>
    <a-row
        justify="center"
        style="padding-bottom:10px"
        type="flex"
    >
      <a-col span="10">
        <a-input
            v-model:value="email"
            :max-length="50"
            placeholder="输入邮箱"
        />
      </a-col>
    </a-row>
    <a-row

        :gutter="8"
        justify="center"
        style="padding-bottom:10px"
        type="flex"
    >
      <a-col span="6">
        <a-input
            v-model:value="code"
            placeholder="输入验证码"
            size="default"
        />
      </a-col>

      <a-col span="4">
        <a-button
            :disabled="!email"
            :loading="btnCodeLoading"
            style="width: 100%"
            type="primary"
            @click="sendCode(email)"
        >
          发送验证码
        </a-button>
      </a-col>
    </a-row>
    <a-row
        align="middle"
        justify="center"
        style="padding-bottom: 8px;padding-top: 16px"
        type="flex"
    >
      <a-col span="2">
        <a-button
            :disabled="!(username&&password&&email&&code)"
            :loading="btnRegisterLoading"
            style="width: 100%"
            type="primary"
            @click="finishRegister"
        >
          注册
        </a-button>
      </a-col>
    </a-row>
    <a-divider/>
    <a-row
        justify="center"
        style="padding-bottom:20px"
        type="flex"
    >
      <a-col span="4">
        <p> 已有账号？</p>
      </a-col>
      <a-col span="2">
        <router-link :to="{name:'Login'}">
          登录
        </router-link>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import axios       from 'axios';
import { message } from 'ant-design-vue';

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      repeatedPassword: '',
      code: '',
      btnCodeLoading: false,
      btnRegisterLoading: false,
    };
  },
  methods: {
    /**
     * 获取邮箱验证码
     * @param email 要发送的邮箱地址
     */
    sendCode(email) {
      this.btnCodeLoading = true;
      axios.post('/v1/global/email', null, { params: { email } })
          .then(
              () => {
                message.success(`验证码已成功发送至${email}`);
              },
          )
          .finally(() => {
            this.btnCodeLoading = false;
          });
    },
    /**
     * 完成注册任务
     */
    finishRegister() {
      this.btnRegisterLoading = true;
      if (this.password !== this.repeatedPassword) {
        message.error('两次输入的密码不一致');
      } else {
        axios.post('/v1/users', {
          username: this.username,
          password: this.password,
          email: this.email,
          code: this.code,
        })
            .then(() => {
              message.success('注册成功！');
              this.$router.push({ name: 'Login' });
            });
      }
      this.btnRegisterLoading = false;
    },
  },
};
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
