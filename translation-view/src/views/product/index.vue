<template>
  <el-card class="container">
    <el-row
        type="flex"
        justify="end"
        align="middle"
        style="margin-bottom: 30px;"
    >
      <el-select v-model="language"
                 placeholder="Select"
                 style="width: 120px;"
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
      <el-table-column prop="content"
                       label="产品描述"
                       align="center"
      />
    </el-table>
  </el-card>
</template>

<script>
import {listProduct} from "@/api/productApi";
import {LANGUAGE_OPTIONS} from "@/data/language";

export default {
  data() {
    return {
      loading: false,
      tableData: [],
      language: 'zh-CN',
      options: LANGUAGE_OPTIONS
    }
  },
  mounted() {
    this.changeLanguage()
  },
  methods: {
    changeLanguage() {
      this.loading = true
      listProduct(this.language).then(res => {
        this.tableData = res.data
        this.loading = false
      })
    }
  }
}
</script>

<style>
.container {
  height: calc(100vh - 210px);
  padding: 20px 60px;
  overflow-y: auto;
}
</style>
