<template>
  <div class="card">
    <div class="card-body">
      <h3 class="page-title" style="margin-bottom:20px;">系统设置</h3>
      <Sidebar/>
      <Other/>
    </div>
  </div>
</template>

<script>
import Sidebar from "@/SettingComponent/Sidebar";
import Other from "@/SettingComponent/Other";

export default {
  name: "Setting",
  components: {Other, Sidebar}
}
</script>

<style scoped>
</style>