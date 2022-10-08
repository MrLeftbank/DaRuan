<template>
    <router-view :key="key"></router-view>
</template>

<script>
export default {
    name: 'container',
    components: {
    },
    computed: {
        key() {
            return this.$route.fullPath;
        }
    },
}
</script>

<style scoped>
</style>