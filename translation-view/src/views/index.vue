<template>
  <div>
    <el-container>
      <el-header>
        <el-row style="height: 100%; width: 100%">
          <el-col :span="4">
            <h2 style="color: white; line-height: 100%">
              GIGA CLOUD TECH
            </h2>
          </el-col>

          <el-col :span="20">
            <el-menu
                mode="horizontal"
                :ellipsis="false"
                @select="handleSelect"
                :default-active="activeMenu"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                class="home-menu"
            >
              <el-menu-item index="translate"
                            class="head-banner"
                            style="font-weight: bold; font-size: 16px"
              >NLLB 翻译</el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </el-header>

      <el-main>
        <router-view v-if="isRouterAlive"/>
      </el-main>

      <el-footer class="footer-copyright">
        <div>
          <h2 style="color: white; font-weight: normal; padding: 0; margin: 0;">
            Copyright 2025 GIGA CLOUD TECH ©All Rights Reserved
          </h2>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      isRouterAlive: true,
      activeMenu: 'product'
    }
  },
  mounted() {
    this.handleSelect('product')
  },
  methods: {
    reload() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    handleSelect() {
      this.activeMenu = 'product'
      this.$router.push('/product')
    }
  }
}
</script>

<style>
.el-header, .el-footer {
  width: 100%;
  height: 80px;
  padding: 0;
  display: flex;
  background-color: #72C1F2;
  color: #333;
  text-align: center;
}

.head-banner {
  font-weight: bold;
  font-size: 16px;
  height: calc(100vh / 4);
  padding: 0 50px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  height: calc(100vh - 120px);
}

.footer-copyright {
  font-size: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.home-menu {
  width: 100%;
  height: 100%;
}

.el-menu--collapse .el-menu .el-submenu, .el-menu--popup {
  min-width: 120px !important;
}

.el-menu--horizontal > .el-menu-item:nth-child(4) {
  margin-right: auto;
}
</style>