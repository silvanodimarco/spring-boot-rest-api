# Product Reviews Web App (Backend) - Spring Boot REST API
## Table of Contents
- [Overview ](#Overview)
- [Installation](#Installation)
- [Domain](#Domain)
  - Database
  - Associations
- [Design Patterns and Other Solutions](#Design-Patterns-and-Other-Solutions)
  - [DTO](#DTO)
  - [Builder](#Builder)
- [Dependencies](#Dependencies)
  - [Spring Data JPA](#Spring-Data-Jpa)
  - [Lombok](#Lombok)
  - [MapStruck](#MapStruck)
- [About Me](#About-Me)


## Overview
This repository contains a simple yet comprehensive REST API built using Spring Boot. The project represents the backend of a web application where you can add reviews of different products.
The main goal of the project and this repository is to showcase my skills in Spring Boot, aiming to deepen and master my knowledge of the framework through practical application. For this reason, 
in this README, I focus on detailing the implementation rather than discussing the project's domain, which could have been anything other than a Product Reviews App.


## Installation
> **_IMPORTANT:_** As of now, this repository does not include a Docker setup to run the application.  
> Updates will incorporate Docker integration in the near future, offering a convenient way to run 
> and test the application effortlessly. For the time being, to maintain security and avoid 
> unintentional exposure of local credentials, the `application.properties` file is intentionally 
> left empty.


## Domain
https://softwareengineering.stackexchange.com/questions/359592/what-is-a-domain

## Design Patterns and Other Solutions
### DTO
A Data Transfer Object (DTO) is an object used to encapsulate and transport data between different layers of an application or different applications. Some of the benefits
of DTOs, and the reasons why I chose to use them in this project, are to efficiently exchange data between different components, minimizing the amount of transferred data,
and to transfer data without exposing the internal details of objects.

In pursuit of optimal software design and to learn the best practices, I chose to design the backend of the project by using a single Request DTO and Response DTO for each distinct endpoint.
The reason for separating Request and Response DTOs is to clearly differentiate the data sent from the client and the data returned from the server. This practice improves the understanding
and maintainability of the code while promoting a more explicit contract between client and server components.
While in a small backend like this it may seem a little bit overhead, given the presence of very similar DTOs that could be reusable, my intention was to treat this project
as if it were a large-scale real-world product.

In the project, the DTOs are placed in the `/api/dto` directory. There you can find not only the different request and response DTOs for each endpoint like `BrandCreateRequestDto.java` but also the DTOs used for handling
error responses from the API like `ApiErrorDto.java` and `ApiValidationErrorDto.java`.

I chose to use Java Records for DTOs because they are preferable to classes when primarily storing data without defining behavior, enabling developers to define DTOs with minimal code and ensuring immutability by default.

### Builder
The Builder pattern is a creational design pattern that allows the developer to construct complex objects step by step.

In the project, the Builder pattern is present through Lombok's `@Builder` annotation which provides a mechanism for using the pattern without writing boilerplate code.
I used the `@Builder` annotation on entities and DTO's class declarations as seen on `ApiValidationErrorDto.java`. Lombok does all the work
behind the curtains, so then I can generate builders to produce instances of the class as seen on the method `handleConstraintValidationException` of
`ErrorHandlingControllerAdvice.java` class in the `/exception` directory.

```java
...
return  ApiValidationErrorDto.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST)
        .path(request.getRequestURI())
        .message(ErrorMessages.VALIDATION_FAILURE.getValue())
        .validations(validations)
        .build();
}
```

## Dependencies
Throughout the project, I integrate various dependencies as part of my learning process and to enhance the overall implementation of the REST API.
This section provides an overview of the project's dependencies, offering a clear understanding of their respective functions and how I have incorporated them 
into the project.

Currently, the dependencies being used include:
- [Spring Data JPA](#Spring-Data-Jpa)
- [Lombok](#Lombok)
- [MapStruck](#MapStruck)

### Spring Data JPA
Spring Data JPA is an integral part of the Spring ecosystem, offering developers a streamlined approach to interact with relational databases using Java Persistence API (JPA).
At its core, Spring Data JPA provides a powerful abstraction layer over JPA, enabling developers to define data access repositories without the need for boilerplate code. 
At the heart of this abstraction is the JpaRepository interface, which provides methods for data manipulation and querying.
By leveraging JpaRepository, developers can focus on defining repository interfaces and let Spring Data JPA handle the implementation details, resulting in cleaner and more maintainable codebases.

The usage of JpaRepository can be observed in the `/persistance/repository` directory, where I declared the repository interfaces extending JpaRepository of the different entities of the project like `BrandRepository.java`.

```java
public interface BrandRepository extends JpaRepository<Brand, Integer> {...}
```

By extending JpaRepository, we get the most relevant CRUD methods for standard data access, including pagination and sorting, for that particular entity. In the case of the
`BrandRepository.java` interface, we can observe its usage in the `BrandService.java` class, where I used methods that are already included like `save`, `findById` and `findAll`.

You can also define more specific access methods in the interface, and there are several different options available for doing so. In this project, I utilized the following:
- Derived Query Methods: Derived method names have two main parts separated by the first _By_ keyword. The first part is the _introducer_, and the rest is the _criteria_. Spring Data JPA supports _find_, _read_, _query_, _count_ and _get_.
In the `BrandRepository.java` interface, I declared a `findByName` method, which returns a `Brand` object and expects a `String` as a parameter.
Spring Data JPA automatically handles the implementation of this method
allowing me to easily utilize it in other parts of the code, such as in the `createBrand` method of the `BrandService.java` class. 
- Native Query: Native queries are simply SQL queries. Using native queries allow us to unleash the full power of our database, as we can use proprietary features not available in JPQL-restricted syntax.
This comes at the cost of losing database portability of our application because our JPA provider can’t abstract specific details from the database implementation or vendor anymore.
As this project is a practice exercise focused on learning through practical implementation, I aimed to explore all the different ways of defining custom methods within the repository. Thus, within the `ProductRepository.java` interface,
I introduced the `getProductsFilteredByName` method. This method retrieves its data from a native query specified by the `@Query` annotation preceding it. Subsequently, I used this method in the `ProductService.java` layer treating it like any other repository method.
```java
public interface ProductRepository extends JpaRepository<Product, Integer> { 
    ...
    @Query(value = "SELECT * FROM product p WHERE p.name LIKE %:name%", nativeQuery = true)
    List<Product> getProductsFilteredByName(
        @Param("name") String name
    );
}
```

Spring Data JPA also provides Specifications which are a way to build complex, dynamic queries for the JPA Repositories.
Some of the advantages of using Spring Data JPA Specifications are:
- Specifications allow you to build complex queries dynamically at runtime. This is especially useful when you have a set of search criteria that can change based on user input or other runtime conditions.
- You can encapsulate query criteria as reusable Specification objects. This promotes code reusability and maintainability, as you can easily use the same criteria in multiple places within the application.
- By avoiding string-based queries, you reduce the risk of SQL injection attacks, as Specifications use parameterized queries by default.
- Spring Data JPA can optimize queries generated from Specifications to minimize database round-trips and improve performance.

To utilize Spring Data JPA Specifications, the initial step involves creating a specification class. In this project, I generated the class named `ReviewSpecification` within the `/persistance/repository` directory. Afterwards, I defined the
methods `filterByRatingAndOrActive` and `isFromProductWithNameLike`, both returning `Specification<Review>`. In these methods, I dynamically constructed the query based on the parameters using predicates with the `criteriaBuilder`. 
```java
public class ReviewSpecification {
    public static Specification<Review> filterByRatingAndOrActive(Integer rating, Boolean active) {
        return (root, query, criteriaBuilder) -> {
            Predicate ratingPredicate = rating != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating) : null;
            Predicate activePredicate = active != null ? criteriaBuilder.equal(root.get("active"), active) : null;

            List<Predicate> predicates = new ArrayList<>();

            if(ratingPredicate != null) {
                predicates.add(ratingPredicate);
            }
            if(activePredicate != null) {
                predicates.add(activePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<Review> isFromProductWithNameLike(String productName) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, Product> reviewsProduct = root.join("product");
            return criteriaBuilder.like(reviewsProduct.get("name"),
                productName != null ? likePattern(productName) : likePattern(""));
        };
    }
  ...
}
```
In addition, I added a new method called `findAll` to the `ReviewRepository` class. This method accepts a parameter of type `Specification<Review>`.
To enable this functionality, the `ReviewRepository` class must now also extend `JpaSpecificationExecutor<Review>`.
```java
public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
  List<Review> findAll(Specification<Review> specification);
}
```
Then, within the `getAllReviews` method of the `ReviewService` class, I instantiate both specifications with their respective parameters. In case it is necessary, I can create a new specification dynamically by combining them using the `.and` method.
To conclude, I call the `findAll` method, passing either a single specification or the combined one as a parameter, and Spring Data JPA handles the rest.
```java
public List<ReviewResponseDto> getAllReviews(Integer rating, Boolean active, String productName) {
        final Specification<Review> filterByRatingAndOrActive = ReviewSpecification.filterByRatingAndOrActive(rating, active);
        final Specification<Review> isFromProductWithNameLike = ReviewSpecification.isFromProductWithNameLike(productName);
        Specification<Review> specification;

        if(rating != null || active != null) {
            specification = filterByRatingAndOrActive.and(isFromProductWithNameLike);
        } else {
            specification = isFromProductWithNameLike;
        }

        List<Review> reviews = reviewRepository.findAll(specification);
        ...
}
```

### Lombok
Lombok is a Java library that helps developers reduce boilerplate code in their projects by providing annotations to generate common code constructs automatically during compilation. It aims to simplify Java development by automating
repetitive tasks such as generating getters, setters, constructors, equals, hashCode, and toString methods.

I used Lombok in this project because it allows me to write cleaner and more concise code while maintaining readability, enabling me to focus on the core logic of the application without getting bogged down by mundane implementation details.

The usage of Lombok can be seen in the entity declarations, such as `Brand.java` in the `/persistance/model` directory, where I utilized Lombok's annotations: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` and `@Builder`.

```java
@Entity
@Table(name = "brand")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    ...
}
```

These annotations allow Lombok to generate all the necessary code automatically at compile time, based on the fields defined for the class.

### MapStruck
MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
Multi-layered applications often require to map between different object models (e.g. entities and DTOs) and writing such mapping code is a tedious and error-prone task.
MapStruct aims at simplifying this work by automating it as much as possible.

Therefore, as I implemented DTOs in this project, I decided to utilize MapStruct for managing the conversions between entities and DTOs.

To use MapStruct, you only need to create the interface and annotate it with the `@Mapper` annotation, as demonstrated in `BrandMapper.java` located in the `/api/mapper` directory.

```java
@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
    ...
}
```

Subsequently, the actual mapping methods must be declared within the interface. These methods expect the source object as parameter and return the target object. In this case, one of the methods
I declared is `brandCreateRequestDtoToBrand` which, as its descriptive name suggests, accepts a `BrandCreateRequestDto` object and returns a `Brand` object. 

```java
Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);
```

Finally, I employed this mapping in the `createBrand`
method of the `BrandService.java` class located in the `/service` directory, accessing it via the member `INSTANCE` declared in the interface, following the convention.

```java
Brand brand = BrandMapper.INSTANCE.brandCreateRequestDtoToBrand(brandCreateRequestDto);
```

## About Me
My name is Silvano Di Marco. I graduated as a Software Engineer in 2023 at Universidad Nacional de San Luis (UNSL) in San Luis, Argentina.

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/silvano-di-marco)

<p style="text-align: center;">🚧 This project is a Work in Progress (WIP) 🚧</p>


