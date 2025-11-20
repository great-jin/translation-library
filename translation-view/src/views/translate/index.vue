<template>
  <el-row :gutter="20" style="height: 100%">
    <!-- 原文输入 -->
    <el-col :span="12">
      <el-card class="card-body">
        <template #header>
          <div class="card-head">原文</div>
        </template>

        <el-row style="margin-bottom: 20px">
          <el-col :span="24">
            <el-button
                @click="clear"
                style="float: right"
            >清空
            </el-button>
          </el-col>
        </el-row>

        <el-row class="input-body">
          <el-col :span="24" style="margin-bottom: 20px">
            <el-input
                type="textarea"
                v-model="reqInfo.text"
                placeholder="请输入需要翻译的文本"
                style="font-size: 16px;"
                :autosize="{minRows: 15}"
                @input="inputText"
                maxlength="5000"
                show-word-limit
                autosize
            />
          </el-col>
        </el-row>
      </el-card>
    </el-col>

    <!-- 翻译结果 -->
    <el-col :span="12" style="height: 100%">
      <el-card class="card-body">
        <template #header>
          <span class="card-head">翻译结果</span>
        </template>

        <el-row style="margin-bottom: 20px">
          <el-col :span="24">
            <el-select
                v-model="reqInfo.targetType"
                placeholder="选择语种"
                @change="changeLanguage"
                style="width: 120px; float: right;"
            >
              <el-option label="中文" value="zh"/>
              <el-option label="English" value="en"/>
              <el-option label="日本語" value="ja"/>
              <el-option label="Deutsch" value="de"/>
              <el-option label="Tiếng Việt" value="vi"/>
              <el-option label="Español" value="es"/>
              <el-option label="Français" value="fr"/>
            </el-select>
            <span style="float: right; line-height: 32px; margin-left: 20px;">目标语种：</span>

            <el-select
                v-model="reqInfo.quality"
                placeholder="选择质量"
                @change="changeQuantity"
                style="width: 80px; float: right;"
            >
              <el-option
                  v-for="num in 6"
                  :key="num"
                  :label="num"
                  :value="num"
              />
            </el-select>
            <span style="float: right; line-height: 32px;">翻译质量：</span>
          </el-col>
        </el-row>

        <el-row class="input-body">
          <el-col :span="24" style="margin-bottom: 20px">
            <el-input
                type="textarea"
                v-model="resultText"
                placeholder="翻译内容将显示在此处"
                style="font-size: 16px"
                :autosize="{minRows: 15}"
                readonly
            />
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import {translate} from "@/api/nllbApi";

export default {
  data() {
    return {
      reqInfo: {
        quality: 2,
        text: '',
        targetType: 'en'
      },
      resultText: ''
    }
  },
  methods: {
    inputText(data) {
      if (data === null || data === '') {
        this.resultText = ''
        return
      }

      this.translation()
    },
    async translation() {
      this.resultText = ''
      if (this.reqInfo.text === '') {
        this.$message.warning('请输入需翻译内容')
        return
      }
      if (this.reqInfo.targetType === '') {
        this.$message.warning('请输入目标语言')
        return
      }

      this.resultText = '内容翻译中...'
      translate(this.reqInfo).then(res => {
        if (res.data !== undefined && res.data !== null) {
          this.resultText = res.data.targetText
        }
      })
    },
    changeLanguage(data) {
      if (this.reqInfo.text === '') {
        return
      }

      this.reqInfo.targetType = data
      this.translation()
    },
    changeQuantity(data) {
      if (this.reqInfo.text === '') {
        return
      }

      this.reqInfo.quality = data
      this.translation()
    },
    clear() {
      this.reqInfo.text = ''
      this.resultText = ''
    }
  }
}
</script>

<style scoped>
.card-head {
  font-size: 18px;
  font-weight: bold;
}

.card-body {
  padding: 10px 5px;
  height: calc(100vh - 190px);
}

.input-body {
  height: calc(100vh - 320px);
  overflow-y: auto;
}
</style>