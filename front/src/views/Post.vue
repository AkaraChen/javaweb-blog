<template>
  <div class="card">
    <div class="card-body">
      <h1>{{ meta.title }}</h1>
      <p>
        {{ meta.time }} {{ meta.category }}
        <i class="ti ti-eye"></i> {{ meta.visit }}
        <i class="ti ti-user"></i>{{ meta.author }}
      </p>
      <p>{{ meta.content }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {useStore} from "vuex";
import {ref} from "vue";
import {useRoute, useRouter} from 'vue-router'

export default {
  name: "Post",
  setup() {
    const store = useStore()
    const baseBackend = store.state.baseBackend;
    const meta = ref(0)
    const route = useRoute()
    const router = useRouter()

    axios.get(baseBackend + '/post/get?id=' + route.params.pid).then(response => {
      meta.value = response.data
    })
    return {meta}
  },
}
</script>