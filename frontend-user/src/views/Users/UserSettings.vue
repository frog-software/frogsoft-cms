<script setup>
import store       from '../../store';
import {DownOutlined, UploadOutlined} from '@ant-design/icons-vue';
function emailAvailable(email) {
  const regex = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  return !regex.test(email);
}
</script>
<template>
  <div class="body">
    <a-tabs>
      <a-tab-pane
          key="1"
          tab="个人信息"
      >
        <a-form
            :label-col="labelCol"
            :wrapper-col="wrapperCol"
        >
          <a-form-item label="头像">
            <a-row
                align="middle"
                justify="space-around"
                type="flex"
            >
              <a-col :span="4">
                <a-avatar
                    :size="64"
                    :src="user.avatar"
                />
              </a-col>
              <a-col :span="4">
                <a-upload
                    v-show="editing"
                    :before-upload="beforeUpload"
                    :custom-request="imageUpload"
                    :show-upload-list="false"
                >
                  <a-button>
                    <template #icon>
                      <UploadOutlined/>
                    </template>
                    更换头像
                  </a-button>
                </a-upload>
              </a-col>
            </a-row>
          </a-form-item>

          <a-form-item label="未来拓展">
            <a-input
                :disabled="true"
                placeholder="未来拓展功能更多功能"
            />
          </a-form-item>

          <a-form-item :wrapper-col="{ span: 24, offset: 5 }">
            <a-button
                v-if="!editing"
                type="primary"
                @click="editing=true"
            >
              编辑
            </a-button>
            <div v-else>
              <a-button
                  type="primary"
                  @click="updateUser().finally(() => {editing = false})"
              >
                提交
              </a-button>
              <a-button @click="editing=false;user=Object.assign({}, $store.getters.user)">
                取消编辑
              </a-button>
            </div>
          </a-form-item>
          <a-form-item
              :wrapper-col="{ span: 12, offset: 5 }"
              label="资料完善度"
          >
            <a-progress
                :percent="percent"
                type="circle"
            />
          </a-form-item>
        </a-form>
      </a-tab-pane>

      <a-tab-pane
          key="2"
          tab="安全设置"
      >
        <a-form
            :label-col="labelCol"
            :wrapper-col="wrapperCol"
        >
          <a-form-item label="用户名">
            <a-input
                v-model:value="user.username"
                placeholder="输入用户名"
            />
            <a-popconfirm
                :title="'确认将用户名从'+currentUsername+'改为'+user.username+'？(该操作需要重新登录)'"
                cancel-text="取消"
                ok-text="确认"
                @cancel="user.username=currentUsername"
                @confirm="updateUser()"
            >
              <a-button
                  :disabled="user.username===currentUsername"
                  danger
              >
                修改用户名
              </a-button>
            </a-popconfirm>
          </a-form-item>

          <a-form-item label="当前密码">
            <a-dropdown :trigger="['click']">
              <a
                  class="ant-dropdown-link"
                  style="color:#ff0000"
                  @click.prevent
              >
                修改密码
                <DownOutlined/>
              </a>
              <template #overlay>
                <a-menu style="padding: 10px">
                  <a-form
                      :label-col="labelCol"
                      :wrapper-col="wrapperCol"
                  >
                    <a-form-item
                        :wrapper-col="{ span: 12, offset: 1 }"
                        label="旧密码"
                    >
                      <a-input-password v-model:value="oldPassword"/>
                    </a-form-item>
                    <a-form-item
                        :wrapper-col="{ span: 12, offset: 1 }"
                        label="新密码"
                    >
                      <a-input-password v-model:value="newPassword"/>
                    </a-form-item>
                    <a-form-item
                        :wrapper-col="{ span: 12, offset: 1 }"
                        label="重复新密码"
                    >
                      <a-input-password v-model:value="confirmPassword"/>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 7 }">
                      <a-button
                          :loading="btnPasswordLoading"
                          danger
                          @click="changePassword"
                      >
                        确认修改
                      </a-button>
                    </a-form-item>
                  </a-form>
                </a-menu>
              </template>
            </a-dropdown>
          </a-form-item>

          <a-form-item label="安全邮箱">
            <a-input
                v-model:value="user.email"
                disabled
            />
            <a-dropdown :trigger="['click']">
              <a
                  class="ant-dropdown-link"
                  style="color:red"
                  @click.prevent
              >
                修改安全邮箱
                <DownOutlined/>
              </a>
              <template #overlay>
                <a-menu>
                  <a-form
                      :label-col="labelCol"
                      :wrapper-col="wrapperCol"
                  >
                    <div style="padding: 10px">
                      修改安全邮箱时，将会发送验证码到旧邮箱以确保为本人操作。
                      <br>
                      暂时不对新邮箱进行验证，若出现问题请联系管理员。
                    </div>
                    <a-form-item
                        :wrapper-col="{ span: 12, offset: 3 }"
                        label="验证码"
                    >
                      <a-row
                          align="middle"
                          justify="start"
                          type="flex"
                      >
                        <a-col :span="16">
                          <a-input v-model:value="emailCode"/>
                        </a-col>
                        <a-col :span="2">
                          <a-button
                              :disabled="emailAvailable(newEmail)"
                              :loading="btnCodeLoading"
                              type="primary"
                              @click="sendCode(user.email)"
                          >
                            发送验证码
                          </a-button>
                        </a-col>
                      </a-row>
                    </a-form-item>
                    <a-form-item
                        :wrapper-col="{ span: 12, offset: 3 }"
                        label="新邮箱"
                    >
                      <a-input v-model:value="newEmail"/>
                    </a-form-item>

                    <a-form-item :wrapper-col="{ span: 12, offset: 7 }">
                      <a-button
                          :disabled="emailCode===''"
                          :loading="btnEmailLoading"
                          danger
                          @click="changeEmail"
                      >
                        确认修改
                      </a-button>
                    </a-form-item>
                  </a-form>
                </a-menu>
              </template>
            </a-dropdown>
          </a-form-item>
        </a-form>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import moment      from 'moment';
import 'moment/locale/zh-cn';
import axios       from 'axios';
import { message } from 'ant-design-vue';

export default {
  name: 'UserSettings',
  components: {DownOutlined, UploadOutlined},
  data() {
    return {
      moment, // 字符串转时间object时需要用到的变量

      // 用户的信息
      user: {
        id: 0,
        username: 'username',
        email: 'pxm@edialect.top',
        avatar: '/avatar.png',
        is_admin: false,
      },
      oldPassword: '', // 用户输入的旧密码
      newPassword: '', // 用户输入的新密码
      confirmPassword: '', // 用户确认的新密码
      newEmail: '', // 用户输入的新邮箱
      emailCode: '', // 用户输入的邮箱验证码
      editing: false, // 是否在编辑状态

      // ui需要用到的变量
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 },
      },

      btnCodeLoading: false,
      btnEmailLoading: false,
      btnPasswordLoading: false,
    };
  },
  computed: {
    /**
     * 资料完成度百分比
     * @returns {number} 资料完成度百分比
     */
    percent() {
      const now = 3.0;
      return parseInt((now * 100) / 8, 10);
    },
    currentUsername() {
      return store.getters.user.username;
    },

  },
  watch: {
    'store.getters.user': function () {
      this.user = { ...store.getters.user };
    },
  },
  created() {
    if (!store.getters.loginStatus) {
      message.error('请先登录');
      this.$router.push({ name: 'Login' });
    }
    this.user = { ...store.getters.user };
  },
  methods: {
    emailAvailable(email) {
      const regex = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      return !regex.test(email)
    },
    /**
     * 修改密码
     */
    changePassword() {
      if (this.newPassword === this.confirmPassword) {
        this.btnPasswordLoading = true;
        axios.put(`/v1/users/${this.user.username}/password`, {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword,
        })
            .then(() => {
              message.success('更改密码成功');
            })
            .catch((err) => {
              switch (err.response.status) {
                case 401: {
                  message.error('原密码错误！');
                  break;
                }
                default: {
                  break;
                }
              }
            })
            .finally(() => {
              this.btnPasswordLoading = false;
            });
      } else {
        message.error('两次输入的密码不一致');
      }
    },
    /**
     * 发送邮箱验证码
     */
    sendCode(email) {
      this.btnCodeLoading = true;
      axios.post('/v1/global/email', null, { params: { email } })
          .then(
              () => {
                message.success(`验证码已成功发送至${email}`);
              },
          )
          .catch(() => {
            message.error('发送失败！');
          })
          .finally(() => {
            this.btnCodeLoading = false;
          });
    },
    /**
     * 修改邮箱按钮
     */
    changeEmail() {
      this.btnEmailLoading = true;
      axios.put(`/v1/users/${this.user.username}/email`, {
        newemail: this.newEmail,
        varyficationcode: this.emailCode,
      })
          .then(() => {
            store.commit('userUpdate');
            message.success('修改成功！');
          })
          .finally(() => {
            this.btnEmailLoading = false;
          });
    },
    /**
     * 更新用户信息
     * @return {Promise<void>}
     */
    async updateUser() {
      return axios.put(`/v1/users/${store.getters.user.username}`, this.user)
          .then((res) => {
            message.success('修改成功！');
            if (this.user.username !== store.getters.user.username) {
              store.commit('userLogout');
              this.$router.push({ name: 'Login' });
            } else {
              store.commit('userUpdate');
            }
          });
    },
    /**
     * 上传图片
     * @param image 即将上传的文件信息
     */
    imageUpload(image) {
      const data = new FormData();
      data.append('file', image.file);
      axios({
        url: '/v1/global/files',
        method: 'post',
        data,
        headers: { 'Content-Type': 'multipart/form-data' },
      })
          .then((res) => {
            this.user.avatar = res.data.uri;
            message.success('成功上传啦~');
          });
    },

    /**
     * 在上传之前检查即将上传的文件
     * @param file 即将上传的文件
     */
    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        message.error('仅支持上传jpg或png文件!');
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        message.error('上传的图片大小不超过2MB!');
      }
      return isJpgOrPng && isLt2M;
    },

  },
};
</script>

<style scoped>
.body {
  background: white;
  padding: 30px;
  width: 70%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
