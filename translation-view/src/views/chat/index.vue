<template>
  <div class="chat-container">
    <!-- 聊天窗口头部 -->
    <div class="chat-header">
      <span>售后服务</span>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-box" @contextmenu.prevent="handleRightClick">
      <div
          v-for="(msg, index) in messages"
          :key="index"
          :class="['msg-item', msg.from]"
      >
        <div class="bubble">
          {{ msg.text }}
        </div>
      </div>
    </div>

    <!-- 自定义右键菜单 -->
    <div
        v-if="contextMenu.visible"
        class="custom-context-menu"
        :style="{ left: contextMenu.x + 'px', top: contextMenu.y + 'px' }"
    >
      <div class="menu-item" @click="handleTranslate">
        <i class="el-icon-position"></i>
        翻译
      </div>
    </div>

    <!-- 翻译结果悬浮窗 -->
    <div
        v-if="translation.visible"
        class="translation-result"
        :style="{ left: translation.x + 'px', top: translation.y + 'px' }"
        @click.stop
    >
      <div class="translation-header">
        <span class="translation-title">翻译结果</span>
        <span class="translation-close" @click="closeTranslation">×</span>
      </div>
      <div v-if="translation.loading" class="translation-loading">
        <i class="el-icon-loading"></i>
        正在翻译中...
      </div>
      <div v-else class="translation-content">
        {{ translation.result }}
      </div>
    </div>

    <!-- 输入框 -->
    <div class="input-bar">
      <el-input
          type="textarea"
          v-model="inputText"
          :autosize="{minRows: 3}"
          placeholder="请输入回复内容"
          @input="dynamicInput"
          clearable
      />
      <el-input
          type="textarea"
          v-model="inputResult"
          :autosize="{minRows: 3}"
          placeholder="翻译结果将显示在此处"
          clearable
      />
      <el-button type="primary"
                 @click="sendMessage"
      >发送</el-button>
    </div>
  </div>
</template>

<script>
import {translate} from "@/api/nllbApi";

export default {
  name: 'ChatTranslation',
  data() {
    return {
      messages: [
        {
          from: '客服',
          text: 'What are your refund policies?'
        }
      ],
      inputText: '',
      inputResult: '',
      selectedText: '',
      contextMenu: {
        visible: false,
        x: 0,
        y: 0
      },
      translation: {
        visible: false,
        x: 0,
        y: 0,
        result: '',
        loading: false
      }
    }
  },
  mounted() {
    this.scrollToBottom();
  },
  methods: {
    sendMessage() {
      const _text = this.inputResult
      if (_text === null || _text === '') {
        return;
      }
      this.messages.push({
        from: "用户",
        text: _text
      });

      this.inputText = "";
      this.inputResult = "";
      this.scrollToBottom();
    },
    async dynamicInput(){
      this.inputResult = ''
      if (this.inputText === '') {
        return
      }
      this.resultText = '内容翻译中...'

      const reqInfo = {
        quality: 2,
        text: this.inputText,
        targetType: 'en'
      }
      await translate(reqInfo).then(res => {
        if (res.data !== undefined && res.data !== null) {
          this.inputResult = res.data.targetText
        }
      })
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const box = document.querySelector(".chat-box");
        if (box) {
          box.scrollTop = box.scrollHeight;
        }
      });
    },
    // 右键事件
    handleRightClick(e) {
      const selected = window.getSelection().toString();
      if (selected === null || selected === '') {
        return;
      }

      this.contextMenu.visible = true
      this.contextMenu.x = e.clientX
      this.contextMenu.y = e.clientY

      this.selectedText = selected
    },
    // 翻译处理
    handleTranslate() {
      this.contextMenu.visible = false;
      const selected = this.selectedText
      if (selected === null || selected === '') {
        return;
      }

      this.translateText();
    },
    // 翻译文本
    async translateText() {
      this.translation.loading = true;
      this.translation.visible = true;
      this.translation.x = this.contextMenu.x;
      this.translation.y = this.contextMenu.y + 30;
      this.translation.result = '文本翻译中'

      try {
        const reqInfo = {
          quality: 2,
          text: this.selectedText,
          targetType: 'zh'
        }
        translate(reqInfo).then(res => {
          const data = res.data
          if (data !== null) {
            this.translation.result = data.targetText
          }
        })
      } catch (error) {
        this.translation.result = '翻译失败，请重试';
      } finally {
        this.translation.loading = false;
      }
    },
    // 关闭翻译结果
    closeTranslation() {
      this.translation.visible = false;
    }
  }
}
</script>

<style scoped>
.chat-container {
  width: 80%;
  height: 90%;
  border: 1px solid #eee;
  display: flex;
  flex-direction: column;
  margin: 30px auto;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  position: relative;
}

.chat-header {
  padding: 15px;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: white;
  text-align: center;
  font-weight: bold;
}

.chat-box {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
  background: #f7f7f7;
  user-select: text;
}

.msg-item {
  margin: 10px 0;
  display: flex;
}

.msg-item.客服 {
  justify-content: flex-start;
}

.msg-item.用户 {
  justify-content: flex-end;
}

.bubble {
  max-width: 60%;
  padding: 10px 14px;
  border-radius: 8px;
  line-height: 20px;
  background: white;
  box-shadow: 0 1px 2px #ddd;
  word-wrap: break-word;
}

.msg-item.客服 .bubble {
  background: #e6f7ff;
  border-top-left-radius: 2px;
}

.msg-item.用户 .bubble {
  background: #95ec69;
  border-top-right-radius: 2px;
}

/* 自定义右键菜单 */
.custom-context-menu {
  position: fixed;
  background: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  z-index: 1000;
  min-width: 160px;
  padding: 5px 0;
}

.menu-item {
  padding: 8px 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  font-size: 14px;
}

.menu-item:hover {
  background-color: #f5f7fa;
}

.menu-item i {
  margin-right: 8px;
  font-size: 16px;
}

.menu-divider {
  height: 1px;
  background-color: #e6e6e6;
  margin: 5px 0;
}

.input-bar {
  padding: 10px;
  display: flex;
  gap: 10px;
  background: white;
  border-top: 1px solid #eee;
}

.translation-result {
  position: fixed;
  background: rgba(0, 0, 0, 0.85);
  color: white;
  padding: 8px 12px;
  border-radius: 6px;
  max-width: 300px;
  z-index: 1001;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  font-size: 14px;
  line-height: 1.4;
  backdrop-filter: blur(5px);
}

.translation-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  padding-bottom: 5px;
}

.translation-title {
  font-weight: bold;
  font-size: 12px;
  color: #95ec69;
}

.translation-close {
  cursor: pointer;
  font-size: 14px;
  color: #ccc;
}

.translation-close:hover {
  color: white;
}

.translation-loading {
  color: #ccc;
  font-style: italic;
  display: flex;
  align-items: center;
}

.translation-loading i {
  margin-right: 5px;
  animation: rotating 2s linear infinite;
}

.translation-content {
  word-break: break-word;
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>