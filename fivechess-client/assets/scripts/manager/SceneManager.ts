import NetManager from "./NetManager";

export default class SceneManager {
    /**
     * 切换场景
     * @param sceneName
     */
    public static loadScene(sceneName) {
        NetManager.inst().setLock(true);
        cc.director.loadScene(sceneName, () => {
            NetManager.inst().setLock(false);
        })
    }
}
