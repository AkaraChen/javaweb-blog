<template>
  <div class="card">
    <div class="card-body">
      <h3 class="card-title">用户添加</h3>
      <div class="mb-3">
        <label class="form-label">用户名</label>
        <input type="text" v-model="username" class="form-control" name="example-text-input" placeholder="在此输入用户名">
      </div>
      <div class="mb-3">
        <label class="form-label">密码</label>
        <input type="password" v-model="passwd1" class="form-control" name="example-password-input"
               placeholder="在此输入密码">
      </div>
      <div class="mb-3">
        <label class="form-label">再次输入密码</label>
        <input type="password" v-model="passwd2" class="form-control" name="example-password-input" placeholder="再输入一次">
      </div>
      <a class="btn btn-blue" @click="update">提交</a>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";
import axios from "axios";
import {useStore} from "vuex";

export default {
  name: "UserAdd",
  setup() {
    const username = ref(undefined)
    const passwd1 = ref(undefined)
    const passwd2 = ref(undefined)
    const baseBackend = useStore().state.baseBackend
    return {username, passwd1, passwd2, baseBackend}
  },
  methods: {
    update() {
      if (this.passwd1 === this.passwd2) {
        if (confirm("确定要添加吗")) {
          axios({
            method: 'POST',
            url: this.baseBackend + '/user/add',
            params: {
              key: 'adminKey',
              name: this.username,
              password: this.passwd1
            }
          })
        }
      } else {
        alert("两个密码不一致")
      }
    }
  }
}
</script>

<style scoped>

</style>