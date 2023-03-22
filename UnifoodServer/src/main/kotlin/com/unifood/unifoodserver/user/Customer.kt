package com.unifood.unifoodserver.user

import jakarta.persistence.*
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType

@Entity
@Table(name = "customer")
open class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false)
    open lateinit var name: String

    @Column(name = "cellphone", nullable = false)
    open lateinit var cellphone: String

    @Column(name = "email", nullable = false)
    open lateinit var email: String

    @Column(name = "password", nullable = false)
    open lateinit var password: String
}