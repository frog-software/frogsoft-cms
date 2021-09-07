import {createStore} from 'vuex';
import axios         from 'axios';
import moment        from 'moment';

moment.locale('zh-cn');

const defaultUser   = {
  id: 0,
  username: 'username',
  nickname: 'nickname',
  email: 'email@frogsoft.com',
  avatar: 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png',
  is_admin: false,
};
const defaultConfig = {
  "favicon": "http://dummyimage.com/100x100",
  "title": "Frogsoft CMS",
  "logo": "http://dummyimage.com/400x400",
  "header": {
    "logo": "http://dummyimage.com/400x200"
  },
  "footer": {
    "logo": "http://dummyimage.com/400x200"
  }
};
const store         = createStore({
  state: {
    tab: [],
    drawerVisibility: false,
    drawerLoading: false,
    user: Object.create(defaultUser),
    publish_articles: [],
    like_articles: [],
    music: 6,
    replyTo: 0,
    commentsLoading: false,
    comments: [
      {
        id: 1234,
        user: {
          id: 0,
          nickname: 'nickname',
          avatar: 'http://dummyimage.com/100x100',
        },
        content: 'content',
        time: '2000-01-01 00:00:00',
        parent: 123,
      },
    ],
    config: Object.assign({}, defaultConfig)
  },
  getters: {
    /**
     * 登录状态
     * @param state
     * @returns {boolean} 是否已经登录
     */
    loginStatus(state) {
      return state.user.id > 0;
    },

    // getter区
    tab(state) {
      return state.tab;
    },
    drawerVisibility(state) {
      return state.drawerVisibility;
    },
    drawerLoading(state) {
      return state.drawerLoading;
    },
    user(state) {
      return state.user;
    },
    publish_articles(state) {
      return state.publish_articles;
    },
    like_articles(state) {
      return state.like_articles;
    },
    music(state) {
      return state.music;
    },
    replyTo(state) {
      return state.replyTo;
    },
    comments(state) {
      return state.comments;
    },
    commentsLoading(state) {
      return state.commentsLoading;
    },
    config(state) {
      return state.config;
    }
  },
  mutations: {
    tab(state, value) {
      const list = ['Home', 'Articles', 'Tools'];
      if (list.indexOf(value[0]) >= 0) {
        state.tab = Object.assign([], value);
      } else state.tab = [];
    },
    drawerVisibility(state, value) {
      state.drawerVisibility = value;
      if (value === true) {
        this.commit('userUpdate');
      }
    },
    userLogin(state, username) {
      if (state.user.username === username) return;
      state.user.username = username
      localStorage.setItem('username', username);
      this.commit('userUpdate');
    },
    userLogout(state) {
      localStorage.removeItem('id');
      localStorage.removeItem('token');
      state.user             = Object.create(defaultUser);
      state.publish_articles = [];
      state.like_articles    = [];
      state.drawerVisibility = false;
    },
    userUpdate(state) {
      state.drawerLoading = true;
      axios.get(`/v1/users/${state.user.username}`).then((res) => {
        state.user             = res.data.user;
        state.publish_articles = res.data.publish_articles;
        state.like_articles    = res.data.like_articles;
        state.drawerLoading    = false;
      });
    },
    changeMusic(state, id) {
      state.music = id;
    },
    changeReplyTo(state, value) {
      state.replyTo = value;
    },
    updateComments(state, id) {
      state.commentsLoading = true;
      return axios.get(`/v1/articles/${id}/comments`).then((res) => {
        state.comments = res.data.comments;
        state.comments.forEach((item) => {
          item.time = moment(item.time).fromNow();
        });
      }).finally(() => {
        state.commentsLoading = false;
      });
    },
    updateConfig(state, id) {
      return axios.get('/v1/global/config/frontend-user').then(res => {
        state.config = Object.create(res.data)
      }).finally(() => {
        document.title                      = state.config.title
        document.getElementById('qwq').href = state.config.favicon
      })
    }
  },
  actions: {},
  modules: {},
});

export default store;
