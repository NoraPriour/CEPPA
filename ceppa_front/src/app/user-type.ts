export type UserId = number
type UserName = string
type UserEmail = string
type KeycloakId = string | null
export type User = {id: UserId, keycloakId: KeycloakId, userName: UserName, email: UserEmail}
export type CreateUser = Pick < User, 'userName' | 'email'> & { temporaryPassword: string }
