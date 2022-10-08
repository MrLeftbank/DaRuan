import { MessageBox } from "element-ui";
export const box = () => {
  return MessageBox.confirm("此操作不可撤回，是否继续？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => true)
    .catch(() => false);
};