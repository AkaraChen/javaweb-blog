<template>
  <div class="card">
    <div class="card-body">
      <label class="form-label">第一张图</label>
      <div><input type="text" v-model="onepic" class="form-control"></div>
      <label class="form-label">第二个设置</label>
      <div><input type="text" v-model="onelink" class="form-control"></div>
      <label class="form-label">第二张图</label>
      <div><input type="text" v-model="twopic" class="form-control"></div>
      <label class="form-label">第二个设置</label>
      <div><input type="text" v-model="twolink" class="form-control"></div>
      <a class="btn btn-blue col-1" @click="update">提交</a>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {ref} from "vue";
import {useStore} from "vuex";

export default {
  name: "Sidebar",
  setup() {
    const onepic = ref(undefined)
    const onelink = ref(undefined)
    const twopic = ref(undefined)
    const twolink = ref(undefined)
    const baseBackend = useStore().state.baseBackend
    return {onepic, onelink, twolink, twopic, baseBackend}
  },
  methods: {
    update() {
      let data = []
      if (!this.onepic) {
        alert("啥也没填")
      } else {
        data.push({
          pic: this.onepic,
          link: this.onelink,
        })
        if (this.twopic) {
          data.push({
            pic: this.twopic,
            link: this.twolink,
          })
        }
      }
      axios({
        method: 'POST',
        url: this.baseBackend + '/setting',
        params: {
          key: "sidebar",
          value: JSON.stringify(data).toString()
        }
      })
    }
  }
}
</script>

<style scoped>
* {
  margin-bottom: 10px;
}
</style>