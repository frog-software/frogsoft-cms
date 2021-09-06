<template>
  <div class="body">
    <!--上方进度条-->
    <a-steps :current="current">
      <a-step
          v-for="item in steps"
          :key="item.title"
          :title="item.title"
      />
    </a-steps>

    <!--进度条内容-->
    <div class="steps-content">
      <div
          v-if="current === 0"
          class="body"
      >
        <h3>请输入要重置密码的用户名</h3>
        <a-input
            v-model:value="username"
            placeholder="输入用户名"
            size="large"
        />
      </div>

      <div
          v-if="current ===1"
          class="body"
      >
        <h3> 已经向用户{{ username }} 绑定的邮箱 {{ email }} 发送验证码，请查收并填写</h3>
        <a-input
            v-model:value="code"
            placeholder="输入验证码"
            size="large"
        />
        <h3> 重置密码</h3>
        <a-input-password
            v-model:value="password"
            placeholder="输入密码"
            size="default"
        />
        <a-input-password
            v-model:value="repeatedPassword"
            placeholder="输入密码"
            size="default"
        />
        <div
            v-show="password!==repeatedPassword"
            style="color: red"
        >
          两次密码不一致
        </div>
      </div>

      <div v-if="current ===2">
        <a-result
          status="success"
          title="您已经成功重置密码！"
        >
          <template #extra>
            <a-button
                type="primary"
                @click="$router.push({name:'Login'})"
            >
              登录
            </a-button>
            <a-button
                key="buy"
                @click="$router.push({name:'Home'})"
            >
              回首页
            </a-button>
          </template>
        </a-result>
      </div>
    </div>

    <!--下方进度条按钮-->
    <div class="steps-action">
      <a-button
          v-if="current < steps.length "
          :loading="btnNextStepLoading"
          type="primary"
          @click="next"
      >
        {{ steps[current].cont }}
      </a-button>
      <a-button
          v-if="current > 0"
          style="margin-left: 8px"
          @click="prev"
      >
        上一步
      </a-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {message} from 'ant-design-vue';

export default {
  name: 'Forget',
  data() {
    return {
      email: '', // 用户输入的用户名对应的邮箱
      username: '', // 用户输入的用户名
      code: '', // 用户输入的验证码
      password: '', // 用户输入的重置的密码
      repeatedPassword: '',

      btnNextStepLoading: false,
      current: 0, // 当前步数
      // 各个步骤
      steps: [
        {
          title: '输入用户名',
          cont: '下一步',
        },
        {
          title: '输入验证信息',
          cont: '下一步',
        },
        {
          title: '成功重置密码',
          cont: '完成',
        },
      ],
    };
  },
  methods: {
    /**
     * 点击按钮下一步触发的事件
     */
    next() {
      this.btnNextStepLoading = true;
      switch (this.current) {
        case 0: {
          if (this.username.length === 0) {
            message.error('请输入用户名');
            break;
          }
          this.current += this.sendCode();
          break;
        }
        case 1: {
          if (this.password !== this.repeatedPassword) break;
          this.current += this.resetPassword();
          break;
        }
        default: {
          message.error('你确定没有写错代码？');
          break;
        }
      }
      this.btnNextStepLoading = false;
    },
    /**
     * 点击按钮上一步触发的事件
     */
    prev() {
      this.current -= 1;
    },
    /**
     * 根据输入的用户名获取邮箱地址存入email中
     * 并向邮箱发送验证码
     */
    sendCode() {
      let result = 0;
      axios.get('/users', {
        username: this.username,
      }).then(
          (res) => {
            if (res.data.users.length === 0) {
              message.error('用户不存在');
            } else {
              this.email = res.data.users[0].email;
              axios.post('/website/email', {email: this.email}).then();
              result = 1;
            }
          },
      );
      return result;
    },
    /**
     * 完成重置功能
     */
    resetPassword() {
      let result = 1;
      axios.put('/login/forget', {
        username: this.username,
        email: this.email,
        code: this.code,
        password: this.password,
      }).then().catch((err) => {
        message.error(err.toString());
        result = 0;
      });
      return result;
    },
  },

};
</script>

<style scoped>
.body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
  padding: 30px;
}

.steps-content {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 32px;
}

.steps-action {
  margin-top: 24px;
}
</style>
