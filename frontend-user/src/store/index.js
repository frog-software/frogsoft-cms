import {createStore} from 'vuex';
import axios         from 'axios';
import moment        from 'moment';

moment.locale('zh-cn');

const defaultUser   = {
  id: 0,
  username: null,
  email: 'email@frogsoft.com',
  avatar: '/avatar.png',
  roles: [],
  is_admin: false,
};
const defaultConfig = {
  favicon: '/frogsoft.svg',
  title: 'Frogsoft CMS',
  logo: '/frogsoft.svg',
  headerDto: {
    logo: '/frogsoft.svg',
  },
  footerDto: {
    logo: '/avatar.png',
  },
};
const store         = createStore({
  state: {
    tab: [],
    drawerVisibility: false,
    drawerLoading: false,
    user: Object.create(defaultUser),
    publishArticles: [],
    favoriteArticles: [],
    statistics: {
      publishArticlesNum: 0,
      viewsNum: 0,
      likesNum: 0,
      favoritesNum: 0,
    },
    replyTo: 0,
    commentsLoading: false,
    comments: [
      {
        id: 1234,
        user: {
          id: 0,
          username: 'username',
          avatar: 'http://dummyimage.com/100x100',
        },
        content: 'content',
        time: '2000-01-01 00:00:00',
        parent: 123,
      },
    ],
    config: {...defaultConfig},
  },
  getters: {
    /**
     * 登录状态
     * @param state
     * @returns {boolean} 是否已经登录
     */
    loginStatus(state) {
      return !!state.user.username;
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
    publishArticles(state) {
      return state.publishArticles;
    },
    favoriteArticles(state) {
      return state.favoriteArticles;
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
    },
    statistics(state) {
      return state.statistics
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
      state.user.username = username.toString();
      localStorage.setItem('username', username);
      this.commit('userUpdate');
    },
    userLogout(state) {
      localStorage.removeItem('username');
      localStorage.removeItem('token');
      state.user             = {...defaultUser};
      state.publishArticles  = [];
      state.favoriteArticles = [];
      state.drawerVisibility = false;
    },
    userUpdate(state) {
      state.drawerLoading = true;
      axios.get(`/v1/users/${state.user.username}`).then((res) => {
        state.user.email       = res.data.email;
        state.user.avatar      = res.data.avatar || defaultUser.avatar;
        state.user.is_admin    = res.data.roles.includes('ROLE_ADMIN');
        state.publishArticles  = res.data.publishArticles;
        state.favoriteArticles = res.data.favoriteArticles;
        state.statistics       = res.data.statistics;
      }).catch(() => {
        this.commit('userLogout');
      }).finally(() => {
        state.drawerLoading = false;
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
        state.comments = res.data?._embedded?.commentDtoList || [];
        state.comments.forEach((item) => {
          item.time = moment(item.time).fromNow();
        });
      }).finally(() => {
        state.commentsLoading = false;
      });
    },
    updateConfig(state) {
      return axios.get('/v1/global/config/frontend-user').then((res) => {
        state.config = {...defaultConfig};
        Object(res.data)?.keys?.forEach((item) => {
          if (typeof (res.data[item]) === 'object') {
            Object(res.data[item])?.keys?.forEach((jtem) => {
              state.config[item][jtem] = res.data[item][jtem];
            });
          } else if (res.data[item]) state.config[item] = res.data[item];
        });
      }).finally(() => {
        document.title                      = state.config.title;
        document.getElementById('qwq').href = state.config.favicon;
      });
    },
  },
  actions: {},
  modules: {},
});

export default store;
