export default interface PostType {
    boardId: string
    boardTitle: string
    boardCreatorId?: string
    boardModifierId?: string
    boardContent?:string
    boardCreatedAt?:string
    boardUpdatedAt?: string
}
