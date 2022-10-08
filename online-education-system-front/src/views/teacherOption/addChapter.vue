<template>
    <div class="ACContainer">
        <el-page-header @back="goBack" content="课程章节" class="header"> </el-page-header>
        <el-divider class="divider"></el-divider>
        <div class="AC">
            <el-button type="primary" @click="chapterFormVisible = true">添加章节</el-button>
            <!-- <el-button @click="test">添加章节</el-button> -->
            <el-divider class="divider"></el-divider>
            <!-- <el-empty v-if="data.length == 0" description="请添加章节"></el-empty> -->
            <el-tree ref="tree" class="tree" lazy :load="loadNode" node-key="id" :data="data" :props="props"
                :render-content="renderContent" :expand-on-click-node="false">
            </el-tree>
            <chapterForm :visible.sync="chapterFormVisible" :data="chapterData" @chapter="addChapter"></chapterForm>
            <sectionForm :visible.sync="sectionFormVisible" :data="sectionData" @section="append"></sectionForm>
            <!-- <sectionForm :visible.sync="sectionFormVisible1" :section_id="1" @section="changeSection"></sectionForm> -->
            <!-- <chapterForm :visible.sync="chapterFormVisible1" :field="chapterData" @chapter="changeChapter"> -->
            <!-- </chapterForm> -->
        </div>
    </div>
</template>

<script>
import sectionForm from "./components/sectionForm"
import chapterForm from "./components/chapterForm"
import { req } from "@/api/request"
export default {
    name: "addChapter",
    props: [
        'course_id'
    ],
    data() {
        return {
            data: [],
            sectionFormVisible: false,
            chapterFormVisible: false,
            chapterData: {
                'course_id': this.course_id,
                'num': 1
            },
            sectionData: '',
            props: {
                label: 'title',
                children: 'children',
                isLeaf: "isLeaf",
            }
        }
    },
    components: {
        sectionForm,
        chapterForm
    },
    methods: {
        addChapter(val) {
            this.chapterData.num++
            this.data.push({
                id: val.id,
                title: val.title,
            })
        },
        getSection(data) {
            this.sectionData = data;
            // this.treeData = data;
            this.sectionFormVisible = true
        },
        async removeChapter(node, data) {
            if (!(await this._box())) return;
            req('/chapter/' + data.id, 'delete')
                .then((res) => {
                    if (res.code != 200) {
                        this.$message.error('删除失败')
                        return
                    }
                    this.$message.success('删除成功')
                    this.remove(node, data)
                })
        },
        async removeSection(node, data) {
            if (!(await this._box())) return;
            req('/section/' + data.id, 'delete')
                .then((res) => {
                    if (res.code != 200) {
                        this.$message.error('删除失败')
                        return
                    }
                    this.$message.success('删除成功')
                    this.remove(node, data)
                })
        },
        remove(node, data) {
            const parent = node.parent;
            const children = parent.data.children || parent.data;
            const children1 = parent.childNodes
            const index = children.findIndex(d => d.id === data.id);
            const index1 = children1.findIndex(d => d.data.id === data.id);
            children.splice(index, 1);
            children1.splice(index1, 1);
        },
        append(val) {
            let data = this.sectionData
            if (!data.children) {
                this.$set(data, 'children', []);
            }
            data.children.push({
                id: val.id,
                title: val.title,
                isLeaf: true,
            });
        },
        renderContent(h, { node, data, store }) {
            return (
                <span class="custom-tree-node tree_node">
                    <span>{node.label}</span>
                    {node.level === 1 ?
                        (<span>
                            <el-button type="primary" class="el-icon-circle-plus-outline" size="mini" on-click={() => { this.getSection(data) }} >
                                添加
                            </el-button >
                            <el-button type="danger" class="el-icon-delete" size="mini" on-click={() => { this.removeChapter(node, data) }} >
                                删除
                            </el-button>
                        </span>) :
                        (<span>
                            <el-button type="danger" class="el-icon-delete" size="mini" on-click={() => { this.removeSection(node, data) }} >
                                删除
                            </el-button >
                        </span>)}
                </span >
            )
        },
        goBack() {
            this.$router.push('/courseManage')
        },
        getChapter() {
            req('/course/' + this.course_id + '/chapter/')
                .then((res) => {
                    if (res.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    this.data = res.data;
                    if (this.data.length == 0) { this.chapterData.num = 1 }
                    else { this.chapterData.num = this.data[this.data.length - 1].number + 1 }
                })
        },
        loadNode(node, resolve) {
            if (node.level != 0)
                req('/chapter/' + node.data.id + '/section/')
                    .then(res => {
                        if (res.code != 200) {
                            this.$message.error('获取失败')
                            return
                        }
                        let data = []
                        if (!node.data.children) {
                            this.$set(node.data, 'children', []);
                        }
                        node.data.children = []
                        res.data.forEach(t => {
                            node.data.children.push({
                                id: t.id,
                                title: t.title,
                                isLeaf: true,
                            })
                        });
                        resolve(data);
                    })
        }
    },
    created() {
        this.getChapter()
    }
};
</script>

<style scoped>
.custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
}

.ACContainer {
    width: 1160px;
    margin: 30px auto;
    background: #FFF;
    border-radius: 15px;
}

.AC {
    padding: 20px;
}

.header {
    margin-left: 20px;
    height: 60px;
    align-items: center;
}

.divider {
    margin-top: 0px;
    margin-bottom: 10px;
}

.tree {
    min-height: 600px;
}

::v-deep .tree_node {
    display: flex;
    justify-content: space-between;
    width: 100%;
}

::v-deep .el-tree-node__content {
    margin-top: 10px;
    padding: 5px 0;
    /* border: 1px solid #14BA4C; */
}
</style>