<template>
  <div v-for="item in meta" style="margin-bottom: 10px" :key="item">
    <div class="card">
      <div class="card-body">
        <router-link :to="'/post/'+item.id"><h3 class="card-title">{{ item.title }}</h3></router-link>
        {{ item.content.substring(0, 80) }}
      </div>
    </div>
  </div>
</template>

<script>
import {useStore} from "vuex";
import axios from "axios";
import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";

export default {
  name: 'Home',
  components: {},
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const baseBackend = store.state.baseBackend
    const meta = ref(undefined)
    axios.get(baseBackend + "/post/list?size=5&id=" + route.params.page).then(response => {
      meta.value = response.data
    })
    return {meta}
  }
}
</script>
