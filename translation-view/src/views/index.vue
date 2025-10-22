<template>
  <div class="container">
    <el-card>
      <el-row
          type="flex"
          justify="center"
          align="middle"
          style="margin-bottom: 10px"
      >
        <h1>NLLB AI 翻译引擎</h1>
      </el-row>

      <el-row
          type="flex"
          justify="end"
          align="middle"
          style="margin-bottom: 50px;"
      >
        <el-select v-model="language"
                   placeholder="Select"
                   style="width: 200px;"
                   size="small"
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
          v-loading="loading"
          :data="tableData"
          style="width: 100%"
          highlight-current-row
      >
        <el-table-column prop="id"
                         label="序号"
                         width="150"
                         align="center"
        />
        <el-table-column prop="name"
                         label="产品名称"
                         width="150"
                         align="center"
        />
        <el-table-column prop="content"
                         label="产品描述"
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
      loading: false,
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
      this.loading = true
      listComments(this.language).then(res => {
        this.tableData = res.data
        this.loading = false
      })
    }
  }
}
</script>

<style>
.container {
  padding: 200px 200px;
}
</style>
