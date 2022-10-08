import { MessageBox } from "element-ui";
export const buy = () => {
    return MessageBox.confirm("确定要购买改课程？", "购买", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
    })
        .then(() => true)
        .catch(() => false);
};