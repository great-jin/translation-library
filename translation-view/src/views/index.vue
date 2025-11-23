<template>
  <div>
    <el-container>
      <el-header style="width: 100%">
        <el-row style="width: 100%">
          <el-col :span="24" style="overflow-x: auto">
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
              <el-menu-item
                  class="head-banner"
                  style="font-weight: bold; font-size: 16px; background: #72C1F2"
              >
                <h2 style="color: white; line-height: 100%;">
                  GIGA CLOUD TECH
                </h2>
              </el-menu-item>
              <el-menu-item
                  v-for="item in menus"
                  :index="item.index"
                  class="head-banner"
                  style="font-weight: bold; font-size: 16px"
              >
                {{ item.name }}
              </el-menu-item>
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
      activeMenu: 'productView',
      menus: [
        {
          index: 'productView',
          name: '列表展示'
        }, {
          index: 'detailView',
          name: '商品详情'
        }, {
          index: 'translateView',
          name: '文本翻译'
        }, {
          index: 'chatView',
          name: '对话翻译'
        }
      ]
    }
  },
  mounted() {
    this.handleSelect('productView')
  },
  methods: {
    reload() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    handleSelect(type) {
      switch (type) {
        case 'productView':
          this.activeMenu = 'productView'
          this.$router.push('/product')
          break;
        case 'detailView':
          this.activeMenu = 'detailView'
          this.$router.push('/detail')
          break;
        case 'translateView':
          this.activeMenu = 'translateView'
          this.$router.push('/translate')
          break;
        case 'chatView':
          this.activeMenu = 'chatView'
          this.$router.push('/chat')
          break;
      }
    }
  }
}
</script>

<style>
.el-header, .el-footer {
  width: 100%;
  padding: 0;
  display: flex;
  background-color: #72C1F2;
  color: #333;
  text-align: center;
}

.head-banner {
  font-weight: bold;
  font-size: 16px;
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
</style>