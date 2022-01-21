<template>
  <div class="card">
    <div class="card-body">
      <h3>用户设置</h3>
      <span class="avatar avatar-lg">{{ meta.name.substring(0, 2) }}</span>
      <div style="margin-top: 10px">
        <label class="form-label">用户名</label>
        <input type="text" class="form-control" v-model="meta.name" readonly="readonly">
      </div>
      <div style="margin-top: 10px">
        <label class="form-label">密码</label>
        <input type="password" class="form-control" placeholder="如果你想更改密码就在此输入，反之不要动" v-model="passwd">
      </div>
      <a class="btn btn-blue" @click="update" style="margin-top: 10px">提交</a>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {useStore} from "vuex";
import {useRoute} from "vue-router";
import {ref} from "vue";

export default {
  name: "User",
  setup() {
    const baseBackend = useStore().state.baseBackend
    const meta = ref({"name": "test", "id": 1})
    axios.get(baseBackend + "/userinfo?id=" + useRoute().params.id).then(response => {
      meta.value = response.data
    })
    const passwd = ref(undefined)
    return {meta, passwd, baseBackend}
  },
  methods: {
    update() {
      if (confirm("确定要更改吗")) {
        axios({
          method: 'POST',
          url: this.baseBackend + '/user/password/change',
          params: {
            id: this.meta.id,
            passwd: this.passwd
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>