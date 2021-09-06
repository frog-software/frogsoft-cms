<template>
  <div class="footer">
    <a-row
        align="middle"
        justify="space-around"
        type="flex"
    >
      <a-col span="3">
        <img
            alt="E方言"
            src="../../assets/logo2.png"
            width="240"
        >
      </a-col>
      <!--            <a-col span="3">-->
      <!--                <a-popover>-->
      <!--   <template v-slot:content> -->
      <!--       <img width="400" src="/src/assets/二维码2.jpg"> -->
      <!--         </template>&ndash;&gt;-->
      <!--                <img width="60" src="/src/assets/二维码1.jpg">-->
      <!--                </a-popover>-->
      <!--            </a-col>-->
      <a-col span="12">
        <h4 style="color: white">
          相关网站：
          <a
              href="https://www.ydict.net/"
              style="color: white"
          >榕典</a> &#8194;|
          <a
              href="https://www.moedict.tw/"
              style="color: white"
          >萌典</a>&#8194;|
          <a
              href="https://fangyan.xunfei.cn/#/"
              style="color: white"
          >讯飞方言保护计划</a>&#8194;|
          <a
              href="https://zhongguoyuyan.cn/"
              style="color: white"
          >中国语言保护工程采录展示平台</a>
        </h4>
        <h4 style="color: white">
          关于我们：
          <router-link
              style="color: white"
              to="/about"
          >
            团队介绍
          </router-link>&#8194;|
          <!--    <router-link style="color: white" to="/contact">联系我们</router-link>&#8194;|-->
          <a
              href=""
              style="color: white"
          >Github</a>
        </h4>
        <h4>Copyrights © {{ new Date().getFullYear() }} Easy Dialect. All Rights Reserved.</h4>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'Footer',
};
</script>

<style scoped>
.footer {
  background: rgb(46, 46, 46);
  width: 100%;
  color: white;
}

h4 {
  color: rgb(199, 199, 199);
}

p {
  font-size: 0.1rem;
  margin: auto;
  color: #8b8b8b;
}
</style>
