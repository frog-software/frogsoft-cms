import{a,r as e,g as t,w as s,o as i,b as l,z as n,B as r,e as u,t as d,d as c}from"./vendor.678eb57e.js";import{a as o}from"./index.fffc2a39.js";const p={name:"UserDetails",components:{ArticleList:o},props:{id:String},data:()=>({spinning:!0,user:{id:0,username:"username",nickname:"nickname",email:"pxm@edialect.top",telephone:"",registration_time:"2001-01-01 00:00:00",login_time:"2001-01-01 00:00:00",birthday:"",avatar:"https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png",county:"",town:"",is_admin:!1},publish_articles:[],like_articles:[]}),created(){a.get(`/users/${this.id}`).then((a=>{this.user=a.data.user,this.publish_articles=a.data.publish_articles,this.like_articles=a.data.like_articles,this.spinning=!1}))}},_=c("h2",null,"用户信息",-1),m=u(" 修改个人信息 ");p.render=function(a,c,o,p,f,b){const h=e("router-link"),g=e("a-button"),k=e("a-descriptions-item"),y=e("a-avatar"),v=e("a-descriptions"),x=e("ArticleList"),z=e("a-tab-pane"),$=e("a-tabs"),j=e("a-card"),w=e("a-spin");return i(),t(w,{spinning:f.spinning,class:"body"},{default:s((()=>[l(j,null,{title:s((()=>[_])),extra:s((()=>[n(l(g,{type:"primary"},{default:s((()=>[l(h,{to:{name:"UserSettings"}},{default:s((()=>[m])),_:1})])),_:1},512),[[r,parseInt(a.$route.params.id)===a.$store.getters.user.id]])])),default:s((()=>[l(v,{column:1},{default:s((()=>[l(k,{label:"用户名"},{default:s((()=>[u(d(f.user.username),1)])),_:1}),l(k,{label:"昵称"},{default:s((()=>[u(d(f.user.nickname),1)])),_:1}),l(k,{label:"头像"},{default:s((()=>[l(y,{size:64,src:f.user.avatar,shape:"circle"},null,8,["src"])])),_:1})])),_:1}),l(v,{layout:"vertical"},{default:s((()=>[l(k,{label:"生日"},{default:s((()=>[u(d(f.user.birthday),1)])),_:1}),l(k,{label:"邮箱"},{default:s((()=>[u(d(f.user.email),1)])),_:1}),l(k,{label:"上次登录时间"},{default:s((()=>[u(d(f.user.login_time),1)])),_:1}),l(k,{label:"电话"},{default:s((()=>[u(d(f.user.telephone),1)])),_:1})])),_:1}),l($,null,{default:s((()=>[l(z,{key:"1",tab:"ta创作的文章"},{default:s((()=>[l(x,{"list-data":f.publish_articles,"page-size":6},null,8,["list-data"])])),_:1}),l(z,{key:"2",tab:"ta收藏的文章"},{default:s((()=>[l(x,{"list-data":f.like_articles,"page-size":6},null,8,["list-data"])])),_:1})])),_:1})])),_:1})])),_:1},8,["spinning"])};export{p as default};
