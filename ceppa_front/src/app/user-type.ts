export type UserId = number
type UserName = string
type UserEmail = string
export type User = {id: UserId, userName: UserName, email: UserEmail}
export type CreateUser = Pick < User, 'userName' | 'email'>