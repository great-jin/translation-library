<template>
  <div class="container">
    <el-card>
      <el-row
          type="flex"
          justify="center"
          align="middle"
          style="margin-bottom: 24px"

      >
        <h1>NLLB AI 翻译引擎</h1>
      </el-row>

      <el-row
          type="flex"
          justify="center"
          align="middle"
          style="margin-bottom: 50px"

      >
        <el-select v-model="language"
                   placeholder="Select"
                   style="width: 200px;"
                   @change="changeLanguage(language)"
        >
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-row>


      <el-table
          :data="tableData"
          style="width: 100%"
          highlight-current-row
      >
        <el-table-column prop="id"
                         label="序号"
                         width="150"
                         align="center"
        />
        <el-table-column prop="content"
                         label="描述"
                         align="center"
        />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import {listComments} from "@/api/commentsApi";

export default {
  data() {
    return {
      tableData: [],
      language: 'zh-CN',
      options:  [
        {
          label: 'Chinese',
          value: 'zh-CN',
        },
        {
          label: 'English',
          value: 'en-US',
        },
        {
          label: 'Japan',
          value: 'ja-JP',
        },
        {
          label: 'Germany',
          value: 'de-DE',
        },
        {
          label: 'Vietnam',
          value: 'vi-VN',
        },
      ]
    }
  },
  mounted() {
    this.changeLanguage()
  },
  methods: {
    changeLanguage() {
      listComments(this.language).then(res => {
        this.tableData = res.data
      })
    }
  }
}
</script>

<style>
.container {
  height: 100vh;
  padding: 200px 200px;
}
</style>
